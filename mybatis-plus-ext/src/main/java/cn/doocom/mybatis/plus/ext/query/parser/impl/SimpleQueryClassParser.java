package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
import cn.doocom.mybatis.plus.ext.query.parser.AbstractQueryClassParser;
import cn.doocom.mybatis.plus.ext.query.parser.QueryFieldParser;
import cn.doocom.util.AnnotationUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleQueryClassParser extends AbstractQueryClassParser {

    public SimpleQueryClassParser() {
        super();
    }

    public SimpleQueryClassParser(QueryFieldParser queryFieldParser) {
        super(queryFieldParser);
    }

    @Override
    public QueryClass parseClass(Class<?> clz, boolean includeSuperclass) {
        QueryClass result = new QueryClass();
        result.setClazz(clz);
        result.setIncludeSuperclass(includeSuperclass);
        List<Field> annotatedFields = AnnotationUtil.getAnnotatedFields(clz, QueryColumn.class, includeSuperclass);
        List<QueryField> queryFields = new ArrayList<>(annotatedFields.size());
        annotatedFields.forEach(e -> queryFields.add(queryFieldParser.parseField(e)));
        result.setQueryFields(queryFields);
        QueryGroup[] queryGroups = clz.getDeclaredAnnotationsByType(QueryGroup.class);
        result.setQueryGroups(Arrays.asList(queryGroups));
        return result;
    }

}
