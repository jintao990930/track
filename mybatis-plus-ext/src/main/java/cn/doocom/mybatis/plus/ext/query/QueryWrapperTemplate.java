package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.parser.QueryWrapperParser;
import cn.doocom.mybatis.plus.ext.query.parser.impl.CacheQueryWrapperParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class QueryWrapperTemplate {

    private final QueryWrapperParser queryWrapperParser;

    public QueryWrapperTemplate() {
        this(new CacheQueryWrapperParser());
    }

    public QueryWrapperTemplate(QueryWrapperParser queryWrapperParser) {
        this.queryWrapperParser = queryWrapperParser;
    }

    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass) {
        return queryWrapperParser.parseWrapper(obj, entityClass);
    }

    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, boolean includeInheritedFields) {
        return queryWrapperParser.parseWrapper(obj, entityClass, includeInheritedFields);
    }

    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, @Nullable QueryWrapperProcessor processor) {
        return queryWrapperParser.parseWrapper(obj, entityClass, processor);
    }

    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, boolean includeInheritedFields, @Nullable QueryWrapperProcessor processor) {
        return queryWrapperParser.parseWrapper(obj, entityClass, includeInheritedFields, processor);
    }

}
