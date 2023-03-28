package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;

public interface QueryClassParser {

    <T> QueryClass<T> parseClass(Class<T> clz);

}
