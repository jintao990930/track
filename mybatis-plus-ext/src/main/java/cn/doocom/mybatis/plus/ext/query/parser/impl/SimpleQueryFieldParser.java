package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.parser.QueryFieldParser;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SimpleQueryFieldParser implements QueryFieldParser {

    @Override
    public QueryField parseField(Field field) {
        field.setAccessible(true);
        QueryField result = new QueryField();
        QueryColumn[] queryColumns = field.getDeclaredAnnotationsByType(QueryColumn.class);
        result.setQueryColumns(Arrays.asList(queryColumns));
        return result;
    }

}
