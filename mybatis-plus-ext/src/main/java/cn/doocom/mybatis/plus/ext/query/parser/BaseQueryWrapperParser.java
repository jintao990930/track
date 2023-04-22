package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperProcessor;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.function.BinaryOperation;
import cn.doocom.mybatis.plus.ext.query.struct.QueryNode;
import cn.doocom.mybatis.plus.ext.query.struct.QueryTree;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class BaseQueryWrapperParser implements QueryWrapperParser {

    protected final QueryClassParser queryClassParser;
    private final ThreadLocal<Map<String, Object>> binaryValueThreadLocal = ThreadLocal.withInitial(() -> new HashMap<>(8));

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

    protected <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, QueryTree tree) {
        return parse(obj, entityClass, tree, null);
    }

    protected <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, QueryTree tree, @Nullable QueryWrapperProcessor processor) {
        QueryWrapper<T> result = Wrappers.query();
        QueryNode root = tree.getRoot();
        doParse(obj, root, result, processor);
        binaryValueThreadLocal.remove();
        return result;
    }

    private <T> void doParse(Object obj, QueryNode node, QueryWrapper<T> wrapper, @Nullable QueryWrapperProcessor processor) {
        node.getWhereBlocksMap().forEach(((field, functionListMap) -> {
            functionListMap.forEach(((function, whereBlocks) -> {
                try {
                    Object value = field.get(obj);
                    if (!function.apply(value)) {
                        return ;
                    }
                    whereBlocks.forEach(block -> {
                        String column = block.getColumn();
                        if (QueryConst.HUMP_2_UNDER_LINE_FLAG.equals(column)) {
                            column = StringUtils.camelToUnderline(field.getName());
                        }
                        if (Logic.OR == block.getInnerLogic()) {
                            wrapper.or();
                        }
                        if (block.getOperation() instanceof BinaryOperation) {
                            Map<String, Object> binaryValueMap = binaryValueThreadLocal.get();
                            Object anotherValue = binaryValueMap.get(column);
                            if (anotherValue == null) {
                                binaryValueMap.put(column, value);
                            } else {
                                block.getOperation().accept(wrapper, column, anotherValue, value);
                                binaryValueMap.remove(column);
                            }
                        } else {
                            block.getOperation().accept(wrapper, column, value);
                        }
                    });
                } catch (IllegalAccessException ignored) {
                    // never happen
                }
            }));
        }));
        List<QueryNode> children = node.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            children.forEach(child -> {
                if (Logic.AND == child.getOuterLogic()) {
                    wrapper.and(w -> doParse(obj, child, w, processor));
                } else if (Logic.OR == child.getOuterLogic()) {
                    wrapper.or(w -> doParse(obj, child, w, processor));
                }
            });
        }
        // do group post process
        if (processor != null) {
            doGroupPostProcess(wrapper, processor.getGroupPostProcessor(node.getGroupId()));
        }
    }

    private void doGroupPostProcess(QueryWrapper<?> wrapper, @Nullable Consumer<QueryWrapper<?>> groupPostProcessor) {
        if (groupPostProcessor != null) {
            groupPostProcessor.accept(wrapper);
        }
    }

}
