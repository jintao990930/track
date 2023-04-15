package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;

import java.util.List;

public class QueryClass {

    private Class<?> clazz;
    private boolean includeInheritedFields;
    private List<QueryField> queryFields;
    private QueryGroup[] queryGroups;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isIncludeInheritedFields() {
        return includeInheritedFields;
    }

    public void setIncludeInheritedFields(boolean includeInheritedFields) {
        this.includeInheritedFields = includeInheritedFields;
    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<QueryField> queryFields) {
        this.queryFields = queryFields;
    }

    public QueryGroup[] getQueryGroups() {
        return queryGroups;
    }

    public void setQueryGroups(QueryGroup[] queryGroups) {
        this.queryGroups = queryGroups;
    }

}
