package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.parser.QueryFieldParser;

import java.lang.reflect.Field;

public class SimpleQueryFieldParser implements QueryFieldParser {

    @Override
    public QueryField parseField(Field field) {
        QueryField result = new QueryField();
        field.setAccessible(true);
        result.setField(field);
        QueryColumn[] queryColumns = field.getDeclaredAnnotationsByType(QueryColumn.class);
        result.setQueryColumns(queryColumns);
        return result;
    }

}
