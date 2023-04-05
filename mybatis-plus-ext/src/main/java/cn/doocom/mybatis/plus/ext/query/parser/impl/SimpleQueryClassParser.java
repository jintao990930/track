package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
import cn.doocom.mybatis.plus.ext.query.parser.BaseQueryClassParser;
import cn.doocom.mybatis.plus.ext.query.parser.QueryFieldParser;
import cn.doocom.util.AnnotationUtil;

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
    public QueryClass parseClass(Class<?> clz, boolean includeInheritedFields) {
        QueryClass result = new QueryClass();
        result.setClazz(clz);
        result.setIncludeInheritedFields(includeInheritedFields);
        List<Field> annotatedFields = AnnotationUtil.getAnnotatedFields(clz, QueryColumn.class, includeInheritedFields);
        List<QueryField> queryFields = new ArrayList<>(annotatedFields.size());
        annotatedFields.forEach(e -> queryFields.add(queryFieldParser.parseField(e)));
        result.setQueryFields(queryFields);
        QueryGroup[] queryGroups = clz.getDeclaredAnnotationsByType(QueryGroup.class);
        List<QueryGroupInfo> queryGroupInfos = new ArrayList<>(queryGroups.length);
        for (QueryGroup queryGroup : queryGroups) {
            queryGroupInfos.add(extract(queryGroup));
        }
        result.setQueryGroupInfos(queryGroupInfos);
        return result;
    }

}
