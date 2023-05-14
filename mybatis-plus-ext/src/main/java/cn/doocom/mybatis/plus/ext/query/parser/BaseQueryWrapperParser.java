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
import java.util.*;
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

    private <T> void doParse(QueryWrapper<T> wrapper, Object obj, QueryNode root, @Nullable QueryOption<T> option) {
        Map<QueryBlock, Object> blockValueMap = extractBlockValueMap(obj, root);
        setCondition(wrapper, blockValueMap);
        List<QueryNode> children = root.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            children.forEach(child -> {
                Map<QueryBlock, Object> childBlockValueMap = extractBlockValueMap(obj, child);
                Optional<Consumer<QueryWrapper<T>>> optionalProcessor = getPostProcessor(child, option);
                boolean emptyMap = CollectionUtils.isEmpty(childBlockValueMap);
                if (emptyMap && !optionalProcessor.isPresent()) {
                    return ;
                }
                wrapper.and(w -> {
                    if (emptyMap) {
                        w.apply("1 = 1");
                    } else {
                        setCondition(w, childBlockValueMap);
                    }
                    optionalProcessor.ifPresent(processor -> processor.accept(w));
                });
            });
        }
        getPostProcessor(root, option).ifPresent(processor -> processor.accept(wrapper));
    }

    private Map<QueryBlock, Object> extractBlockValueMap(Object obj, QueryNode node) {
        Map<QueryBlock, Object> result = new HashMap<>(8);
        node.getQueryBlocksMap().forEach((field, outerMap) -> {
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException ignored) {
                // would never happen
            }
            Object finalValue = value;
            outerMap.forEach((check, queryBlocks) -> {
                if (!check.apply(finalValue)) {
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

    private <T> Optional<Consumer<QueryWrapper<T>>> getPostProcessor(QueryNode node, @Nullable QueryOption<T> option) {
        if (option == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(option.getPostProcessor(node.getGroupId()));
    }

}
