package site.lianwu.mybatis.plus.query.model;

import site.lianwu.mybatis.plus.query.NestedQuery;
import site.lianwu.mybatis.plus.query.processor.NestedQueryFieldProcessor;

import java.lang.reflect.Field;

/**
 * 嵌套查询字段，对{@link NestedQuery}作用的{@code Field}的封装
 *
 * @author LianWu
 * @see NestedQuery
 * @see NestedQueryFieldProcessor
 */
public class NestedQueryField extends QueryField {

    /**
     * 查询类，是提取{@link NestedQuery}的结果封装
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
