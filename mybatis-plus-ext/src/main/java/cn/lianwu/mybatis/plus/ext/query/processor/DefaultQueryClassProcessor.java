package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.model.QueryField;
import cn.lianwu.mybatis.plus.ext.query.reflection.Reflections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的查询类处理器，支持查询类缓存，实现对查询类的提取和解析功能
 *
 * @author LianWu
 */
public class DefaultQueryClassProcessor extends BaseQueryClassProcessor {

    /**
     * 是否启用查询类缓存
     */
    private final boolean queryClassCacheEnabled;

    /**
     * 查询类缓存
     */
    private final Map<String, QueryClass> queryClassCache;

    /**
     * 默认构造方法，默认启用查询类缓存，查询字段处理器包含{@link SimpleQueryFieldProcessor}和{@link NestedQueryFieldProcessor}
     */
    public DefaultQueryClassProcessor() {
        this(true);
    }

    /**
     * 带缓存启用选项的构造方法，查询字段处理器包含{@link SimpleQueryFieldProcessor}和{@link NestedQueryFieldProcessor}
     *
     * @param queryClassCacheEnabled 是否启用查询类缓存
     */
    public DefaultQueryClassProcessor(boolean queryClassCacheEnabled) {
        super();
        this.queryClassCacheEnabled = queryClassCacheEnabled;
        queryClassCache = queryClassCacheEnabled ? new ConcurrentHashMap<>(32) : null;
    }

    /**
     * 带查询字段处理器集合的构造方法，默认启用查询类缓存
     *
     * @param queryFieldProcessors 查询字段处理器集合，用于提取和解析查询字段
     */
    public DefaultQueryClassProcessor(Collection<QueryFieldProcessor> queryFieldProcessors) {
        this(queryFieldProcessors, true);
    }

    /**
     * 带查询字段处理器集合和缓存启用选项的构造方法
     *
     * @param queryFieldProcessors 查询字段处理器集合，用于提取和解析查询字段
     * @param queryClassCacheEnabled 是否启用查询类缓存
     */
    public DefaultQueryClassProcessor(Collection<QueryFieldProcessor> queryFieldProcessors, boolean queryClassCacheEnabled) {
        super(queryFieldProcessors);
        this.queryClassCacheEnabled = queryClassCacheEnabled;
        queryClassCache = queryClassCacheEnabled ? new ConcurrentHashMap<>(32) : null;
    }

    /**
     * 提取给定类的查询信息，如果启用了缓存，则尝试先从缓存中获取
     *
     * @param clz 待提取查询信息的类
     * @param includeInheritedFields 是否包含继承字段
     * @return 查询类信息
     */
    @Override
    public QueryClass extract(Class<?> clz, boolean includeInheritedFields) {
        if (queryClassCacheEnabled) {
            String cacheKey = generateQueryClassCacheKey(clz, includeInheritedFields);
            QueryClass queryClass = queryClassCache.get(cacheKey);
            if (queryClass == null) {
                queryClassCache.putIfAbsent(cacheKey, doExtractClass(clz, includeInheritedFields));
                queryClass = queryClassCache.get(cacheKey);
            }
            return queryClass;
        }
        return doExtractClass(clz, includeInheritedFields);
    }


    /**
     * 解析给定的查询类和对象，生成可执行的查询语句列表
     *
     * @param queryClass 查询类信息
     * @param obj 待查询的对象
     * @return 可执行的查询语句列表
     */
    @Override
    public List<ExecutableQueryStatement> parse(QueryClass queryClass, Object obj) {
        List<QueryField> queryFields = queryClass.getQueryFields();
        List<ExecutableQueryStatement> executableQueryStatements = new ArrayList<>((int) (queryFields.size() * 1.3));
        queryFields.forEach(queryField -> {
            Object fieldValue = Reflections.getFieldValue(queryField.getField(), obj);
            for (QueryFieldProcessor fieldProcessor : queryFieldProcessors) {
                executableQueryStatements.addAll(fieldProcessor.parse(queryField, fieldValue));
            }
        });
        return executableQueryStatements;
    }

    /**
     * 生成查询类缓存的键（类的全限定名 + 是否包含继承字段）
     *
     * @param clz 类
     * @param includeInheritedFields 是否包含继承字段
     * @return 缓存键
     */
    private String generateQueryClassCacheKey(Class<?> clz, boolean includeInheritedFields) {
        String cacheKey = clz.getName();
        if (includeInheritedFields) {
            return cacheKey + "#includeInheritedFields";
        }
        return cacheKey;
    }

}
