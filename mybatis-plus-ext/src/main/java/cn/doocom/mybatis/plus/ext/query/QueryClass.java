package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;

import java.util.List;

public class QueryClass {

    private final Class<?> sourceClass;
    private final boolean includedSuperclasses;
    private final List<QueryField> queryFields;
    private final List<QueryGroup> queryGroups;

    public QueryClass(Class<?> sourceClass, boolean includedSuperclasses,
                      List<QueryField> queryFields, List<QueryGroup> queryGroups) {
        this.sourceClass = sourceClass;
        this.includedSuperclasses = includedSuperclasses;
        this.queryFields = queryFields;
        this.queryGroups = queryGroups;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public boolean isIncludedSuperclasses() {
        return includedSuperclasses;
    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public List<QueryGroup> getQueryGroups() {
        return queryGroups;
    }

}
