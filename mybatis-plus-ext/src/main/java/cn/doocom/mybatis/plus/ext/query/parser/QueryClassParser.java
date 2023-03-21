package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryWrapperBean;

public interface QueryClassParser {

    default <T> QueryWrapperBean<T> parseClass(Class<T> clz) {
        return parseClass(clz, false);
    }

    <T> QueryWrapperBean<T> parseClass(Class<T> clz, boolean includeSuperclass);

}
