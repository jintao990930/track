package cn.lianwu.mybatis.plus.ext.query.parser;

import cn.lianwu.mybatis.plus.ext.query.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.QueryField;
import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.model.QueryBlock;
import cn.lianwu.mybatis.plus.ext.query.model.QueryNode;
import cn.lianwu.mybatis.plus.ext.query.model.QueryTree;
import cn.lianwu.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.*;
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

    protected <T> QueryWrapper<T> parse(Object obj, QueryTree tree, QueryOption<T> option) {
        QueryWrapper<T> result = Wrappers.query();
        QueryNode root = tree.getRoot();
        doParse(result, obj, root, option);
        return result;
    }

    private <T> void doParse(QueryWrapper<T> wrapper, Object obj, QueryNode parent, QueryOption<T> option) {
        Map<QueryBlock, Object> blockValueMap = extractBlockValueMap(obj, parent);
        setCondition(wrapper, blockValueMap);
        List<QueryNode> children = parent.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            children.forEach(child -> {
                Map<QueryBlock, Object> childBlockValueMap = extractBlockValueMap(obj, child);
                Optional<Consumer<QueryWrapper<T>>> childProcessor = getPostProcessor(option, child.getGroupId());
                boolean emptyChildBlockValueMap = CollectionUtils.isEmpty(childBlockValueMap);
                if (emptyChildBlockValueMap && !childProcessor.isPresent()) {
                    return ;
                }
                wrapper.and(w -> {
                    if (emptyChildBlockValueMap) {
                        w.apply("1 = 1");
                    } else {
                        setCondition(w, childBlockValueMap);
                    }
                    childProcessor.ifPresent(p -> p.accept(w));
                });
            });
        }
        getPostProcessor(option, parent.getGroupId()).ifPresent(p -> p.accept(wrapper));
    }

    private Map<QueryBlock, Object> extractBlockValueMap(Object obj, QueryNode node) {
        Map<QueryBlock, Object> result = new HashMap<>(8);
        node.getField2ConditionalQueryBlocksMap().forEach((field, conditionalQueryBlocks) -> {
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException ignored) {
                // would never happen
            }
            Object finalValue = value;
            conditionalQueryBlocks.forEach((condition, queryBlocks) -> {
                if (!condition.apply(finalValue)) {
                    return ;
                }
                for (QueryBlock block : queryBlocks) {
                    result.put(block, finalValue);
                }
            });
        });
        return result;
    }

    private <T> void setCondition(QueryWrapper<T> wrapper, Map<QueryBlock, Object> blockValueMap) {
        blockValueMap.forEach((block, value) -> {
            if (block.getLogic() == Logic.OR) {
                wrapper.or();
            }
            block.getOperation().accept(wrapper, block.getColumn(), value);
        });
    }

    private <T> Optional<Consumer<QueryWrapper<T>>> getPostProcessor(QueryOption<T> option, String groupId) {
        if (option == null) {
            return Optional.empty();
        }
        return option.getPostProcessor(groupId);
    }

}
