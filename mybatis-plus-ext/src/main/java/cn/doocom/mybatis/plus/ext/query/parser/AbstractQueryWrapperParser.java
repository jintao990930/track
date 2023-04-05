package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
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
    public QueryGroupInfo extract(QueryGroup queryGroup) {
        return queryClassParser.extract(queryGroup);
    }

    @Override
    public QueryField parseField(Field field) {
        return queryClassParser.parseField(field);
    }

    @Override
    public QueryColumnInfo extract(QueryColumn queryColumn) {
        return queryClassParser.extract(queryColumn);
    }

}
