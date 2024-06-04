package cn.lianwu.mybatis.plus.ext.query.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryClass {

    private final Class<?> sourceClass;

    private final boolean includeInheritedFields;

    private final List<QueryField> queryFields;

    private final Set<String> groupIds;

    public QueryClass(Class<?> sourceClass, boolean includeInheritedFields, List<QueryField> queryFields) {
        this.sourceClass = sourceClass;
        this.includeInheritedFields = includeInheritedFields;
        this.queryFields = queryFields;
        groupIds = queryFields.stream().flatMap(queryField -> queryField.getGroupIds().stream()).collect(Collectors.toSet());
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

    public Set<String> getGroupIds() {
        return groupIds;
    }

}
