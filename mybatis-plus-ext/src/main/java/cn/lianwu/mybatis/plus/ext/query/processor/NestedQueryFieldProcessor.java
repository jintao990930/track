package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.NestedQuery;
import cn.lianwu.mybatis.plus.ext.query.model.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NestedQueryFieldProcessor implements QueryFieldProcessor {

    protected QueryClassProcessor queryClassProcessor;

    public NestedQueryFieldProcessor(QueryClassProcessor queryClassProcessor) {
        this.queryClassProcessor = queryClassProcessor;
    }

    @Override
    public Optional<QueryField> extract(Field field) {
        NestedQuery nestedQuery = field.getDeclaredAnnotation(NestedQuery.class);
        if (nestedQuery == null) {
            return Optional.empty();
        }
        QueryClass queryClass = queryClassProcessor.extract(field.getDeclaringClass(), nestedQuery.value());
        return Optional.of(new NestedQueryField(field, queryClass));
    }

    @Override
    public List<ExecutableQueryStatement> parse(QueryField queryField, Object fieldValue) {
        if (!(queryField instanceof NestedQueryField)) {
            return Collections.emptyList();
        }
        NestedQueryField nestedQueryField = (NestedQueryField) queryField;
        return queryClassProcessor.parse(nestedQueryField.getQueryClass(), fieldValue);
    }

}
