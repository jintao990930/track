package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;

import java.util.List;

public class QueryClass {

    private final Class<?> sourceClass;
    private final boolean includeInheritedFields;
    private final List<QueryField> queryFields;
    private final QueryGroup[] queryGroups;

    public QueryClass(Class<?> sourceClass, boolean includeInheritedFields,
                      List<QueryField> queryFields, QueryGroup[] queryGroups) {
        this.sourceClass = sourceClass;
        this.includeInheritedFields = includeInheritedFields;
        this.queryFields = queryFields;
        this.queryGroups = queryGroups;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public boolean isIncludeInheritedFields() {
        return includeInheritedFields;
    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public QueryGroup[] getQueryGroups() {
        return queryGroups;
    }

}
