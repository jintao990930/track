package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;

import java.util.List;

public class QueryClass<T> {

    private Class<T> clz;
    private boolean includeSuperclass;
    private List<QueryField> queryFields;
    private List<QueryGroup> queryGroups;

    public Class<T> getClz() {
        return clz;
    }

    public void setClz(Class<T> clz) {
        this.clz = clz;
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
