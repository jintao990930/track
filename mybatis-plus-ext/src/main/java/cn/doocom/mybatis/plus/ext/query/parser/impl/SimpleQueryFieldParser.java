package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.parser.QueryFieldParser;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SimpleQueryFieldParser implements QueryFieldParser {

    @Override
    public QueryField parseField(Field field) {
        QueryField result = new QueryField();
        field.setAccessible(true);
        result.setField(field);
        QueryColumn[] queryColumns = field.getDeclaredAnnotationsByType(QueryColumn.class);
        result.setQueryColumns(Arrays.asList(queryColumns));
        return result;
    }

    @Override
    public QueryColumnInfo extract(QueryColumn queryColumn) {
        QueryColumnInfo result = new QueryColumnInfo();
        result.setOperation(queryColumn.value().getOperation());
        result.setColumn(queryColumn.column());
        result.setGroupId(queryColumn.groupId());
        result.setLogic(queryColumn.logic());
        result.setCheck(queryColumn.check().getExpression());
        return result;
    }

}
