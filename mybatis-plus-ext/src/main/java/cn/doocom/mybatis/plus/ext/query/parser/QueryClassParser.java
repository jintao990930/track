package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;

public interface QueryClassParser extends QueryFieldParser {

    default QueryClass parseClass(Class<?> clz) {
        return parseClass(clz, false);
    }

    QueryClass parseClass(Class<?> clz, boolean includeSuperclass);

    QueryGroupInfo extract(QueryGroup queryGroup);

}
