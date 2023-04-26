package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryOption;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.struct.QueryNode;
import cn.doocom.mybatis.plus.ext.query.struct.QueryTree;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;
import cn.doocom.mybatis.plus.ext.query.struct.QueryBlock;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseQueryWrapperParser implements QueryWrapperParser {

    protected final QueryClassParser queryClassParser;

    public BaseQueryWrapperParser() {
        this(new SimpleQueryClassParser());
    }

    public BaseQueryWrapperParser(QueryClassParser queryClassParser) {
        this.queryClassParser = queryClassParser;
    }

    @Override
    public QueryClass parseClass(Class<?> clz, boolean includeInheritedFields) {
        return queryClassParser.parseClass(clz, includeInheritedFields);
    }

    @Override
    public QueryField parseField(Field field) {
        return queryClassParser.parseField(field);
    }

    protected <T> QueryWrapper<T> parse(Object obj, QueryTree tree) {
        return parse(obj, tree, null);
    }

    protected <T> QueryWrapper<T> parse(Object obj, QueryTree tree, @Nullable QueryOption<T> option) {
        QueryWrapper<T> result = Wrappers.query();
        QueryNode root = tree.getRoot();
        doParse(obj, root, result, option);
        return result;
    }

    private <T> void doParse(Object obj, QueryNode node, QueryWrapper<T> wrapper, @Nullable QueryOption<T> option) {
        node.getQueryBlocksMap().forEach(((field, checkQueryBlocksMap) -> {
            checkQueryBlocksMap.forEach(((check, queryBlocks) -> {
                Object value = null;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException ignored) {
                    // would never happen
                }
                if (!check.apply(value)) {
                    return ;
                }
                for (QueryBlock block : queryBlocks) {
                    String column = block.getColumn();
                    if (QueryConst.HUMP_2_UNDER_LINE_FLAG.equals(column)) {
                        column = StringUtils.camelToUnderline(field.getName());
                    }
                    if (Logic.OR == block.getInnerLogic()) {
                        wrapper.or();
                    }
                    block.getOperation().accept(wrapper, column, value);
                }
            }));
        }));
        List<QueryNode> children = node.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            children.forEach(child -> {
                if (Logic.AND == child.getOuterLogic()) {
                    wrapper.and(w -> doParse(obj, child, w, option));
                } else if (Logic.OR == child.getOuterLogic()) {
                    wrapper.or(w -> doParse(obj, child, w, option));
                }
            });
        }
        // handle processor
        if (option != null) {
            doPostProcess(wrapper, option.getPostProcessor(node.getGroupId()));
        }
    }

    private <T> void doPostProcess(QueryWrapper<T> wrapper, @Nullable Consumer<QueryWrapper<T>> postProcessor) {
        if (postProcessor != null) {
            postProcessor.accept(wrapper);
        }
    }

}
