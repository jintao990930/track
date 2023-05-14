package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.parser.BaseQueryClassParser;
import cn.doocom.mybatis.plus.ext.query.parser.QueryFieldParser;
import cn.doocom.common.util.AnnotationUtil;

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
        List<Field> annotatedFields = AnnotationUtil.getAnnotatedFields(clz, QueryColumn.class, includedSuperclasses);
        List<QueryField> queryFields = new ArrayList<>(annotatedFields.size());
        annotatedFields.forEach(e -> queryFields.add(queryFieldParser.parseField(e)));
        return new QueryClass(clz, includedSuperclasses, queryFields);
    }

}
