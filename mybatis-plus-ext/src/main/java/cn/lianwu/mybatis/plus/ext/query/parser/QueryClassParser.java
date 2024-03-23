package cn.lianwu.mybatis.plus.ext.query.parser;

import cn.lianwu.mybatis.plus.ext.query.QueryClass;

public interface QueryClassParser extends QueryFieldParser {

    QueryClass parseClass(Class<?> clz, boolean includedSuperclasses);

}
