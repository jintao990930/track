package cn.doocom.mybatis.ext.parser;

import cn.doocom.mybatis.ext.QueryWrapperBean;

public interface QueryWrapperParser {

    default <T> QueryWrapperBean<T> parseClass(Class<T> clz) {
        return parseClass(clz, false);
    }

    <T> QueryWrapperBean<T> parseClass(Class<T> clz, boolean includeSuperclass);

}
