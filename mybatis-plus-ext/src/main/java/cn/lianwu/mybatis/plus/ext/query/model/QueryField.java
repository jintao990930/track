package cn.lianwu.mybatis.plus.ext.query.model;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 查询字段，对查询注解所作用的{@code Field}的封装
 *
 * @author LianWu
 * @see SimpleQueryField
 * @see NestedQueryField
 */
public abstract class QueryField {

    /**
     * 字段
     */
    protected final Field field;

    /**
     * 分组ID集合，提取查询注解中声明的分组ID
     */
    protected final Collection<String> groupIds;

    protected QueryField(Field field, Collection<String> groupIds) {
        this.field = field;
        this.groupIds = groupIds;
    }

    public Field getField() {
        return field;
    }

    public Collection<String> getGroupIds() {
        return groupIds;
    }

}
