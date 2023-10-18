package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;

public interface QueryClassParser extends QueryFieldParser {

    QueryClass parseClass(Class<?> clz, boolean includedSuperclasses);

}
