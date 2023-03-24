package cn.doocom.mybatis.plus.ext.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@FunctionalInterface
public interface BinaryOperation extends BaseOperation {

    @Override
    default void accept(QueryWrapper<?> wrapper, String column, Object... values) {
        accept(wrapper, column, values[0], values[1]);
    }

    void accept(QueryWrapper<?> wrapper, String column, Object value1, Object value2);

}
