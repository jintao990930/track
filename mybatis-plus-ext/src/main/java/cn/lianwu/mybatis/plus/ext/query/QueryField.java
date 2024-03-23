package cn.lianwu.mybatis.plus.ext.query;

import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;

import java.lang.reflect.Field;

public class QueryField {

    private final Field field;
    private final QueryColumn[] queryColumns;

    public QueryField(Field field, QueryColumn[] queryColumns) {
        this.field = field;
        this.queryColumns = queryColumns;
    }

    public Field getField() {
        return field;
    }

    public QueryColumn[] getQueryColumns() {
        return queryColumns;
    }

}
