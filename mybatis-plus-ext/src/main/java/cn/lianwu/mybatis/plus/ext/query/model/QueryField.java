package cn.lianwu.mybatis.plus.ext.query.model;

import java.lang.reflect.Field;
import java.util.Set;

public abstract class QueryField {

    protected final Field field;

    protected final Set<String> groupIds;

    protected QueryField(Field field, Set<String> groupIds) {
        this.field = field;
        this.groupIds = groupIds;
    }

    public Field getField() {
        return field;
    }

    public Set<String> getGroupIds() {
        return groupIds;
    }

}
