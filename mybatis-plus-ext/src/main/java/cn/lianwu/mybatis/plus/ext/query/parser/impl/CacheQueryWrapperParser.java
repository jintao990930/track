package cn.lianwu.mybatis.plus.ext.query.parser.impl;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.model.QueryTree;
import cn.lianwu.mybatis.plus.ext.query.parser.QueryClassParser;
import cn.lianwu.mybatis.plus.ext.query.parser.BaseQueryWrapperParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public class CacheQueryWrapperParser extends BaseQueryWrapperParser {

    protected final ConcurrentHashMap<String, SoftReference<QueryTree>> cache = new ConcurrentHashMap<>(64);

    public CacheQueryWrapperParser() {
        super();
    }

    public CacheQueryWrapperParser(QueryClassParser queryClassParser) {
        super(queryClassParser);
    }

    @Override
    public <T> QueryWrapper<T> parseWrapper(Object obj, boolean includedSuperclasses, QueryOption<T> option) {
        String cacheKey = generateCacheKey(obj.getClass(), includedSuperclasses);
        SoftReference<QueryTree> queryTreeRef = cache.computeIfAbsent(cacheKey, key -> {
            QueryTree queryTree = new QueryTree(parseClass(obj.getClass(), includedSuperclasses));
            return new SoftReference<>(queryTree);
        });
        QueryTree queryTree = queryTreeRef.get();
        if (queryTree == null) {
            queryTree = new QueryTree(parseClass(obj.getClass(), includedSuperclasses));
            cache.put(cacheKey, new SoftReference<>(queryTree));
        }
        return parse(obj, queryTree, option);
    }

    protected String generateCacheKey(Class<?> clz, boolean includedSuperclasses) {
        String cacheKey = clz.getName();
        if (includedSuperclasses) {
            return cacheKey + "#includedSuperclasses";
        }
        return cacheKey;
    }

}
