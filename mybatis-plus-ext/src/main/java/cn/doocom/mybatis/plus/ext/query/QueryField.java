package cn.doocom.mybatis.plus.ext.query;

import java.lang.reflect.Field;
import java.util.List;

public class QueryField {

    private Field field;
    private List<QueryColumnInfo> queryColumnInfos;

    public QueryField() { }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<QueryColumnInfo> getQueryColumnInfos() {
        return queryColumnInfos;
    }

    public void setQueryColumnInfos(List<QueryColumnInfo> queryColumnInfos) {
        this.queryColumnInfos = queryColumnInfos;
    }

}
