package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryOption;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.model.QueryNode;
import cn.doocom.mybatis.plus.ext.query.model.QueryTree;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;
import cn.doocom.mybatis.plus.ext.query.model.QueryBlock;
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
    public QueryClass parseClass(Class<?> clz, boolean includedSuperclasses) {
        return queryClassParser.parseClass(clz, includedSuperclasses);
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
        doParse(result, obj, root, option);
        return result;
    }

    private <T> void doParse(QueryWrapper<T> wrapper, Object obj, QueryNode node, @Nullable QueryOption<T> option) {
        boolean with1eq1 = true;
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
                    continue ;
                }
                with1eq1 = false;
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
            children.forEach(child -> wrapper.and(w -> doParse(w, obj, child, option)));
        }
        // handle processor
        Consumer<QueryWrapper<T>> postProcessor;
        if (option != null
                && (postProcessor = option.getPostProcessor(node.getGroupId())) != null) {
            postProcessor.accept(wrapper);
        }
        if (with1eq1 && !node.isRoot()) {
            wrapper.apply("1 = 1");
        }
    }

}
