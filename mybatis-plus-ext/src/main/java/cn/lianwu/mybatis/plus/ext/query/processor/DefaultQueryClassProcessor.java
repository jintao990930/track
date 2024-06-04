package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.model.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.QueryField;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultQueryClassProcessor extends BaseQueryClassProcessor {

    protected final Map<String, SoftReference<QueryClass>> queryClassCache = new ConcurrentHashMap<>(32);

    public DefaultQueryClassProcessor() {
        super();
    }

    public DefaultQueryClassProcessor(Collection<QueryFieldProcessor> queryFieldProcessors) {
        super(queryFieldProcessors);
    }

    @Override
    public QueryClass extract(Class<?> clz, boolean includeInheritedFields) {
        String cacheKey = generateQueryClassCacheKey(clz, includeInheritedFields);
        SoftReference<QueryClass> queryClassSortReference = queryClassCache.computeIfAbsent(cacheKey, key -> new SoftReference<>(doExtractClass(clz, includeInheritedFields)));
        QueryClass queryClass = queryClassSortReference.get();
        if (queryClass == null) {
            queryClass = doExtractClass(clz, includeInheritedFields);
            queryClassCache.put(cacheKey, new SoftReference<>(queryClass));
        }
        return queryClass;
    }

    @Override
    public List<ExecutableQueryStatement> parse(QueryClass queryClass, Object obj) {
        List<QueryField> queryFields = queryClass.getQueryFields();
        List<ExecutableQueryStatement> executableQueryStatements = new ArrayList<>((int) (queryFields.size() * 1.5));
        queryFields.forEach(queryField -> {
            Object fieldValue = null;
            try {
                fieldValue = queryField.getField().get(obj);
            } catch (IllegalAccessException ignored) {
                // would never happen
            }
            for (QueryFieldProcessor fieldProcessor : queryFieldProcessors) {
                executableQueryStatements.addAll(fieldProcessor.parse(queryField, fieldValue));
            }
        });
        return executableQueryStatements;
    }

    protected String generateQueryClassCacheKey(Class<?> clz, boolean includeInheritedFields) {
        String cacheKey = clz.getName();
        if (includeInheritedFields) {
            return cacheKey + "#includeInheritedFields";
        }
        return cacheKey;
    }

}
