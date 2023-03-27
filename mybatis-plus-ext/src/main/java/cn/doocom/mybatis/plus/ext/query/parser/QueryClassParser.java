package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;

public interface QueryClassParser {

    QueryClass parseClass(Class<?> clz);

}
