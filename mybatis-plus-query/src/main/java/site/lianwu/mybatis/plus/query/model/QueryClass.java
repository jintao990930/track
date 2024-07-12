package site.lianwu.mybatis.plus.query.model;

import site.lianwu.mybatis.plus.query.processor.QueryClassProcessor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询类，对条件对象所属类的封装，包含查询配置和查询字段等
 *
 * @author LianWu
 * @see QueryClassProcessor
 */
public class QueryClass {

    /**
     * 条件对象所在类
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
    private final Set<String> groupIds;

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

    public Set<String> getGroupIds() {
        return groupIds;
    }

}
