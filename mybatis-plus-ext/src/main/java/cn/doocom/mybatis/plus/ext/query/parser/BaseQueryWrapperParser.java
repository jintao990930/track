package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import cn.doocom.mybatis.plus.ext.query.model.QueryNode;
import cn.doocom.mybatis.plus.ext.query.model.QueryTree;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BaseQueryWrapperParser implements QueryWrapperParser {

    protected final QueryClassParser queryClassParser;
    protected ThreadLocal<Map<Class<?>, Object>> binaryValueThreadLocal = ThreadLocal.withInitial(() -> new HashMap<>(8));

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
        QueryWrapper<T> result = Wrappers.query();
        // 扩展点1
        QueryNode root = tree.getRoot();
        doParse(obj, root, result);
        binaryValueThreadLocal.remove();
        // 扩展点4
        return result;
    }

    private <T> void doParse(Object obj, QueryNode node, QueryWrapper<T> wrapper) {
        // 扩展点2
        node.getWhereBlocksMap().forEach(((field, functionListMap) -> {
            functionListMap.forEach(((function, whereBlocks) -> {
                try {
                    Object value = field.get(obj);
                    if (!function.apply(value)) {
                        return ;
                    }
                    whereBlocks.forEach(block -> {
                        String column = block.getColumn();
                        if (Objects.equals(column, QueryConst.HUMP_2_UNDER_LINE_FLAG)) {
                            column = StringUtils.camelToUnderline(field.getName());
                        }
                        if (Objects.equals(block.getInnerLogic(), Logic.OR)) {
                            wrapper.or();
                        }
                        if (Objects.equals(block.getOperatorType(), Operator.Type.BINARY)) {
                            Map<Class<?>, Object> binaryValueMap = binaryValueThreadLocal.get();
                            Class<?> type = field.getType();
                            Object anotherValue = binaryValueMap.get(type);
                            if (Objects.isNull(anotherValue)) {
                                binaryValueMap.put(type, value);
                            } else {
                                block.getOperation().accept(wrapper, column, anotherValue, value);
                                binaryValueMap.remove(type);
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
                if (Objects.equals(child.getOuterLogic(), Logic.AND)) {
                    wrapper.and(w -> doParse(obj, child, w));
                } else if (Objects.equals(child.getOuterLogic(), Logic.OR)) {
                    wrapper.or(w -> doParse(obj, child, w));
                }
            });
        }
        // 扩展点3
    }

}
