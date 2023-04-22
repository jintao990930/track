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

    public <T> QueryWrapper<T> parse(Object obj) {
        return queryWrapperParser.parseWrapper(obj);
    }

    public <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields) {
        return queryWrapperParser.parseWrapper(obj, includeInheritedFields);
    }

    public <T> QueryWrapper<T> parse(Object obj, @Nullable QueryWrapperProcessor<T> processor) {
        return queryWrapperParser.parseWrapper(obj, processor);
    }

    public <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields, @Nullable QueryWrapperProcessor<T> processor) {
        return queryWrapperParser.parseWrapper(obj, includeInheritedFields, processor);
    }

}
