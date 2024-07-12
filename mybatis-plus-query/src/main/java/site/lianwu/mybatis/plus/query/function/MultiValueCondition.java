package site.lianwu.mybatis.plus.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Arrays;
import java.util.Collection;

/**
 * 多值查询接口，定义构建多值查询条件行为
 *
 * @author LianWu
 * @see ConditionType.In
 * @see ConditionType.NotIn
 */
@FunctionalInterface
public interface MultiValueCondition extends Condition {

    /**
     * 对多值查询条件的通用逻辑处理
     * <pre>
     * 重写{@link #accept(QueryWrapper, String, Object)}，将值转为{@code Collection}对象值，并调用由子类实现的{@link #accept(QueryWrapper, String, Collection)}。
     * 如果值既不是{@code Collection}也不是{@code Array}类型，则抛出IllegalArgumentException。
     * </pre>
     *
     * @param wrapper 查询包装器，用于封装查询条件。
     * @param column 列名，条件中涉及的列。
     * @param value 值，指定列的条件值。
     * @throws IllegalArgumentException 如果值既不是{@code Collection}也不是{@code Array}类型
     */
    @Override
    @SuppressWarnings("unchecked")
    default void accept(QueryWrapper<?> wrapper, String column, Object value) {
        Collection<Object> params;
        if (value instanceof Collection) {
            // 判断value是否为集合类型，如果是，则直接转换为Collection对象
            params = (Collection<Object>) value;
        } else if (value.getClass().isArray()) {
            // 如果value是数组类型，则转换为List对象
            params = Arrays.asList((Object[]) value);
        } else {
            // 如果value既不是集合也不是数组类型，则抛出异常
            throw new IllegalArgumentException(String.format("%s必须是Array或者Collection类型", value));
        }
        // 构建多值查询条件
        accept(wrapper, column, params);
    }

    /**
     * 接受一个查询包装器、列名和多个值，用于构建多值的查询条件
     *
     * @param wrapper 查询包装器，用于封装查询条件
     * @param column 列名，条件中涉及的列
     * @param values 多值，指定列的条件值
     */
    void accept(QueryWrapper<?> wrapper, String column, Collection<Object> values);

}
