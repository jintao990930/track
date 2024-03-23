package cn.lianwu.mybatis.plus.ext.query.parser.impl;

import cn.lianwu.mybatis.plus.ext.query.QueryField;
import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.parser.QueryFieldParser;

import java.lang.reflect.Field;

public class SimpleQueryFieldParser implements QueryFieldParser {

    @Override
    public QueryField parseField(Field field) {
        field.setAccessible(true);
        QueryColumn[] queryColumns = field.getDeclaredAnnotationsByType(QueryColumn.class);
        return new QueryField(field, queryColumns);
    }

}
