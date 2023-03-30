package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryFieldParser;

import java.lang.reflect.Field;

public abstract class AbstractQueryClassParser implements QueryClassParser {

    protected final QueryFieldParser queryFieldParser;

    public AbstractQueryClassParser() {
        this(new SimpleQueryFieldParser());
    }

    public AbstractQueryClassParser(QueryFieldParser queryFieldParser) {
        this.queryFieldParser = queryFieldParser;
    }

    @Override
    public QueryField parseField(Field field) {
        return queryFieldParser.parseField(field);
    }

}
