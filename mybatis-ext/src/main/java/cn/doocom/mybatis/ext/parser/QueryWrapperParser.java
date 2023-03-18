package cn.doocom.mybatis.ext.parser;

import cn.doocom.mybatis.ext.QueryWrapperBean;

public interface QueryWrapperParser {

    default <T> QueryWrapperBean<T> parseClass(Class<?> clz) {
        return parseClass(clz, false);
    }

    <T> QueryWrapperBean<T> parseClass(Class<?> clz, boolean includeSuperClass);

}
