package cn.lianwu.mybatis.plus.ext.query.model;

import java.lang.reflect.Field;

public class NestedQueryField extends QueryField {

    private final QueryClass queryClass;

    public NestedQueryField(Field field, QueryClass queryClass) {
        super(field, queryClass.getGroupIds());
        this.queryClass = queryClass;
    }

    public QueryClass getQueryClass() {
        return queryClass;
    }

}
