package cn.doocom.mybatis.plus.ext.query.parser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryClassParser {

    default <T> QueryWrapper<T> parseClass(Class<T> clz) {
        return parseClass(clz, false);
    }

    <T> QueryWrapper<T> parseClass(Class<T> clz, boolean includeSuperclass);

}
