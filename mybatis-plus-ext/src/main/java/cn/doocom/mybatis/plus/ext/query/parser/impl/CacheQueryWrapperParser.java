package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.parser.QueryWrapperParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;

public class CacheQueryWrapperParser implements QueryWrapperParser {

    private final QueryWrapperParser queryWrapperParser;

    public CacheQueryWrapperParser() {
        this(new SimpleQueryWrapperParser());
    }

    public CacheQueryWrapperParser(QueryWrapperParser queryWrapperParser) {
        this.queryWrapperParser = queryWrapperParser;
    }

    @Override
    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, boolean includeSuperclass) {
        return null;
    }

    @Override
    public QueryClass parseClass(Class<?> clz, boolean includeSuperclass) {
        return queryWrapperParser.parseClass(clz, includeSuperclass);
    }

    @Override
    public QueryField parseField(Field field) {
        return queryWrapperParser.parseField(field);
    }

}
