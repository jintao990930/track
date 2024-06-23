package cn.lianwu.mybatis.plus.ext.query.model;

import java.lang.reflect.Field;

/**
 * 嵌套查询字段，对{@link cn.lianwu.mybatis.plus.ext.query.NestedQuery}作用的{@code Field}的封装
 *
 * @author LianWu
 * @see cn.lianwu.mybatis.plus.ext.query.NestedQuery
 * @see cn.lianwu.mybatis.plus.ext.query.processor.NestedQueryFieldProcessor
 */
public class NestedQueryField extends QueryField {

    /**
     * 查询类，是提取{@link cn.lianwu.mybatis.plus.ext.query.NestedQuery}的结果封装
     */
    private final QueryClass queryClass;

    public NestedQueryField(Field field, QueryClass queryClass) {
        super(field, queryClass.getGroupIds());
        this.queryClass = queryClass;
    }

    public QueryClass getQueryClass() {
        return queryClass;
    }

}
