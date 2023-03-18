package cn.doocom.mybatis.ext.parser;

import cn.doocom.mybatis.ext.QueryWrapperBean;

public interface QueryWrapperParser {

    <T> QueryWrapperBean<T> parseClass(Class<?> clz);

}
