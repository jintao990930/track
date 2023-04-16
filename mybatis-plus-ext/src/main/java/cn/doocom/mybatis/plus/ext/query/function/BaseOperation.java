package cn.doocom.mybatis.plus.ext.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@FunctionalInterface
public interface BaseOperation {

    void accept(QueryWrapper<?> wrapper, String column, Object... values);

}
