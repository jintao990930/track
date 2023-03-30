package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;

import java.lang.reflect.Field;

public abstract class AbstractQueryWrapperParser implements QueryWrapperParser {

    protected final QueryClassParser queryClassParser;

    public AbstractQueryWrapperParser() {
        this(new SimpleQueryClassParser());
    }

    public AbstractQueryWrapperParser(QueryClassParser queryClassParser) {
        this.queryClassParser = queryClassParser;
    }

    @Override
    public QueryClass parseClass(Class<?> clz, boolean includeSuperclass) {
        return queryClassParser.parseClass(clz, includeSuperclass);
    }

    @Override
    public QueryField parseField(Field field) {
        return queryClassParser.parseField(field);
    }

}
