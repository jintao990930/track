package cn.doocom.mybatis.plus.ext.query;

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
        return parse(obj, entityClass, false);
    }

    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, boolean includeInheritedFields) {
        return queryWrapperParser.parseWrapper(obj, entityClass, includeInheritedFields);
    }

}
