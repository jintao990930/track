package cn.lianwu.mybatis.plus.ext.query.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询类，对查询对象的类封装，包含查询配置和查询字段等
 *
 * @author LianWu
 * @see cn.lianwu.mybatis.plus.ext.query.processor.QueryClassProcessor
 */
public class QueryClass {

    /**
     * 查询对象所在类
     */
    private final Class<?> clazz;

    /**
     * 是否包含继承字段
     */
    private final boolean includeInheritedFields;

    /**
     * 查询字段列表
     */
    private final List<QueryField> queryFields;

    /**
     * 分组ID集合，提取查询字段中的分组ID
     */
    private final Collection<String> groupIds;

    public QueryClass(Class<?> clazz, boolean includeInheritedFields, List<QueryField> queryFields) {
        this.clazz = clazz;
        this.includeInheritedFields = includeInheritedFields;
        this.queryFields = queryFields;
        groupIds = queryFields.stream().flatMap(queryField -> queryField.getGroupIds().stream()).collect(Collectors.toSet());
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public boolean isIncludeInheritedFields() {
        return includeInheritedFields;
    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public Collection<String> getGroupIds() {
        return groupIds;
    }

}
