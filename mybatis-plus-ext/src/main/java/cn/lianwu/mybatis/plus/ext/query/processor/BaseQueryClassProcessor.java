package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.NestedQuery;
import cn.lianwu.mybatis.plus.ext.query.QueryCondition;
import cn.lianwu.mybatis.plus.ext.query.QueryConditionContainer;
import cn.lianwu.mybatis.plus.ext.query.model.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.model.QueryField;
import cn.lianwu.mybatis.plus.ext.query.util.Annotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseQueryClassProcessor implements QueryClassProcessor {

    protected final Collection<QueryFieldProcessor> queryFieldProcessors;

    protected BaseQueryClassProcessor() {
        queryFieldProcessors = new ArrayList<>();
        queryFieldProcessors.add(new SimpleQueryFieldProcessor());
        queryFieldProcessors.add(new NestedQueryFieldProcessor(this));
    }

    protected BaseQueryClassProcessor(Collection<QueryFieldProcessor> queryFieldProcessors) {
        this.queryFieldProcessors = queryFieldProcessors;
    }

    protected QueryClass doExtractClass(Class<?> clz, boolean includeInheritedFields) {
        List<Field> annotatedFields = Annotations.listAnyAnnotatedFields(clz, includeInheritedFields, QueryCondition.class, QueryConditionContainer.class, NestedQuery.class);
        List<QueryField> queryFields = new ArrayList<>(annotatedFields.size());
        annotatedFields.forEach(field -> {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            queryFieldProcessors.forEach(queryFieldProcessor -> queryFieldProcessor.extract(field).ifPresent(queryFields::add));
        });
        return new QueryClass(clz, includeInheritedFields, queryFields);
    }

}
