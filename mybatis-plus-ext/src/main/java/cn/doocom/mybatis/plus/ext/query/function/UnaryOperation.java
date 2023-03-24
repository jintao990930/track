package cn.doocom.mybatis.plus.ext.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@FunctionalInterface
public interface UnaryOperation extends BaseOperation {

    @Override
    default void accept(QueryWrapper<?> wrapper, String column, Object... values) {
        accept(wrapper, column, values[0]);
    }

    void accept(QueryWrapper<?> wrapper, String column, Object value);

}
