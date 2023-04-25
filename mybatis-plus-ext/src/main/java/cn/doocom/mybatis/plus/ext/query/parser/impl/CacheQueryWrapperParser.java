package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryOption;
import cn.doocom.mybatis.plus.ext.query.struct.QueryTree;
import cn.doocom.mybatis.plus.ext.query.parser.BaseQueryWrapperParser;
import cn.doocom.mybatis.plus.ext.query.parser.QueryClassParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.concurrent.ConcurrentHashMap;

public class CacheQueryWrapperParser extends BaseQueryWrapperParser {

    private final ConcurrentHashMap<String, QueryTree> cache = new ConcurrentHashMap<>();

    public CacheQueryWrapperParser() {
        super();
    }

    public CacheQueryWrapperParser(QueryClassParser queryClassParser) {
        super(queryClassParser);
    }

    @Override
    public <T> QueryWrapper<T> parseWrapper(Object obj, boolean includeInheritedFields, @Nullable QueryOption<T> processor) {
        String cacheKey = generateCacheKey(obj.getClass(), includeInheritedFields);
        QueryTree queryTree = cache.computeIfAbsent(cacheKey, key -> {
            QueryClass queryClass = parseClass(obj.getClass(), includeInheritedFields);
            return new QueryTree(queryClass);
        });
        return parse(obj, queryTree, processor);
    }

    protected String generateCacheKey(Class<?> clz, boolean includeInheritedFields) {
        String className = clz.getName();
        if (includeInheritedFields) {
            return className + "#includeInheritedFields";
        }
        return className;
    }

}
