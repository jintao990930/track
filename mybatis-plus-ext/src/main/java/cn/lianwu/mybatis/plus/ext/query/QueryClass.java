package cn.lianwu.mybatis.plus.ext.query;

import java.util.List;

public class QueryClass {

    private final Class<?> sourceClass;
    private final boolean includedSuperclasses;
    private final List<QueryField> queryFields;

    public QueryClass(Class<?> sourceClass, boolean includedSuperclasses, List<QueryField> queryFields) {
        this.sourceClass = sourceClass;
        this.includedSuperclasses = includedSuperclasses;
        this.queryFields = queryFields;
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

}
