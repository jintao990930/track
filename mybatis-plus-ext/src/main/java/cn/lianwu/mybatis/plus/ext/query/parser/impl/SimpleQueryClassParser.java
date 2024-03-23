package cn.lianwu.mybatis.plus.ext.query.parser.impl;

import cn.lianwu.mybatis.plus.ext.query.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.QueryField;
import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumns;
import cn.lianwu.mybatis.plus.ext.query.parser.BaseQueryClassParser;
import cn.lianwu.mybatis.plus.ext.query.parser.QueryFieldParser;
import cn.lianwu.mybatis.plus.ext.query.utils.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SimpleQueryClassParser extends BaseQueryClassParser {

    public SimpleQueryClassParser() {
        super();
    }

    public SimpleQueryClassParser(QueryFieldParser queryFieldParser) {
        super(queryFieldParser);
    }

    @Override
    public QueryClass parseClass(Class<?> clz, boolean includedSuperclasses) {
        List<Field> annotatedFields = AnnotationUtils.getAnnotatedFields(clz, includedSuperclasses, QueryColumn.class, QueryColumns.class);
        List<QueryField> queryFields = new ArrayList<>(annotatedFields.size());
        annotatedFields.forEach(e -> queryFields.add(queryFieldParser.parseField(e)));
        return new QueryClass(clz, includedSuperclasses, queryFields);
    }

}
