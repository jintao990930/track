package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;

import java.util.List;

public class QueryClass {

    private Class<?> clazz;
    private boolean includeSuperclass;
    private List<QueryField> queryFields;
    private List<QueryGroup> queryGroups;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isIncludeSuperclass() {
        return includeSuperclass;
    }

    public void setIncludeSuperclass(boolean includeSuperclass) {
        this.includeSuperclass = includeSuperclass;
    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<QueryField> queryFields) {
        this.queryFields = queryFields;
    }

    public List<QueryGroup> getQueryGroups() {
        return queryGroups;
    }

    public void setQueryGroups(List<QueryGroup> queryGroups) {
        this.queryGroups = queryGroups;
    }
}
