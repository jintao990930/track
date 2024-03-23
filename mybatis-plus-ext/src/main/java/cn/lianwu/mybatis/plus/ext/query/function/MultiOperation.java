package cn.lianwu.mybatis.plus.ext.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@FunctionalInterface
public interface MultiOperation extends BaseOperation {

    @Override
    default void accept(QueryWrapper<?> wrapper, String column, Object... values) {
        Collection<Object> params;
        if (values[0].getClass().isArray()) {
            Object[] arr = (Object[]) values[0];
            params = new ArrayList<>(arr.length);
            Collections.addAll(params, arr);
        } else if (values[0] instanceof Collection) {
            params = (Collection<Object>) values[0];
        } else {
            throw new IllegalArgumentException("The value should be of type Array or Collection.");
        }
        accept(wrapper, column, params);
    }

    void accept(QueryWrapper<?> wrapper, String column, Collection<Object> values);

}
