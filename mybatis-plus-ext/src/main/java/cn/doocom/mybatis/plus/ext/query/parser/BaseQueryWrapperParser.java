package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryOption;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.struct.QueryNode;
import cn.doocom.mybatis.plus.ext.query.struct.QueryTree;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;
import cn.doocom.mybatis.plus.ext.query.struct.QueryBlock;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

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
        Set<Map.Entry<Field, Map<Function<Object, Boolean>, List<QueryBlock>>>> outerEntrySet = node.getQueryBlocksMap().entrySet();
        for (Map.Entry<Field, Map<Function<Object, Boolean>, List<QueryBlock>>> outerEntry : outerEntrySet) {
            Field field = outerEntry.getKey();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException ignored) {
                // would never happen
            }
            Set<Map.Entry<Function<Object, Boolean>, List<QueryBlock>>> innerEntrySet = outerEntry.getValue().entrySet();
            for (Map.Entry<Function<Object, Boolean>, List<QueryBlock>> innerEntry : innerEntrySet) {
                Function<Object, Boolean> checkFunction = innerEntry.getKey();
                if (!checkFunction.apply(value)) {
                    return ;
                }
                List<QueryBlock> queryBlocks = innerEntry.getValue();
                for (QueryBlock block : queryBlocks) {
                    if (Logic.OR == block.getInnerLogic()) {
                        wrapper.or();
                    }
                    block.getOperation().accept(wrapper, block.getColumn(), value);
                }
            }
        }
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
