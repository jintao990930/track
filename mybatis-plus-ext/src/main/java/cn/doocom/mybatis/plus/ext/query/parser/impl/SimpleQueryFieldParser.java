package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.parser.BaseQueryFieldParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SimpleQueryFieldParser extends BaseQueryFieldParser {

    @Override
    public QueryField parseField(Field field) {
        QueryField result = new QueryField();
        field.setAccessible(true);
        result.setField(field);
        QueryColumn[] queryColumns = field.getDeclaredAnnotationsByType(QueryColumn.class);
        List<QueryColumnInfo> queryColumnInfos = new ArrayList<>(queryColumns.length);
        for (QueryColumn queryColumn : queryColumns) {
            queryColumnInfos.add(extract(queryColumn));
        }
        result.setQueryColumnInfos(queryColumnInfos);
        return result;
    }

}
