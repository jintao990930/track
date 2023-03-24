package cn.doocom.mybatis.plus.ext.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Arrays;
import java.util.Collection;

@FunctionalInterface
public interface MultiOperation extends BaseOperation {

    @Override
    default void accept(QueryWrapper<?> wrapper, String column, Object... values) {
        accept(wrapper, column, Arrays.asList(values));
    }

    void accept(QueryWrapper<?> wrapper, String column, Collection<Object> values);

}
