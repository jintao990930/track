package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;

public interface QueryClassParser extends QueryFieldParser {

    default QueryClass parseClass(Class<?> clz) {
        return parseClass(clz, false);
    }

    QueryClass parseClass(Class<?> clz, boolean includeInheritedFields);

}
