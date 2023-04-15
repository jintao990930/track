package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryFieldParser;

import java.lang.reflect.Field;

public abstract class BaseQueryClassParser implements QueryClassParser {

    protected final QueryFieldParser queryFieldParser;

    public BaseQueryClassParser() {
        this(new SimpleQueryFieldParser());
    }

    public BaseQueryClassParser(QueryFieldParser queryFieldParser) {
        this.queryFieldParser = queryFieldParser;
    }

    @Override
    public QueryField parseField(Field field) {
        return queryFieldParser.parseField(field);
    }

}
