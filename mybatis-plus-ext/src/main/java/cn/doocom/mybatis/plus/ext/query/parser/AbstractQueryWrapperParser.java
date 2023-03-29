package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.parser.impl.SimpleQueryClassParser;

public abstract class AbstractQueryWrapperParser implements QueryWrapperParser {

    protected QueryClassParser queryClassParser;

    public AbstractQueryWrapperParser() {
        this(SimpleQueryClassParser.getInstance());
    }

    public AbstractQueryWrapperParser(QueryClassParser queryClassParser) {
        this.queryClassParser = queryClassParser;
    }

    @Override
    public QueryClass parseClass(Class<?> clz, boolean includeSuperclass) {
        return queryClassParser.parseClass(clz, includeSuperclass);
    }

}
