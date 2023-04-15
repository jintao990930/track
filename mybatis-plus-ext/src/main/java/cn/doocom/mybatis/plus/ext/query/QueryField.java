package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;

import java.lang.reflect.Field;

public class QueryField {

    private Field field;
    private QueryColumn[] queryColumns;

    public QueryField() { }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public QueryColumn[] getQueryColumns() {
        return queryColumns;
    }

    public void setQueryColumns(QueryColumn[] queryColumns) {
        this.queryColumns = queryColumns;
    }

}
