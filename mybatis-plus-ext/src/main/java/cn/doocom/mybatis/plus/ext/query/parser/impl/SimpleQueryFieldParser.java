package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.parser.QueryFieldParser;

import java.lang.reflect.Field;

public class SimpleQueryFieldParser implements QueryFieldParser {

    @Override
    public QueryField parseField(Field field) {
        return null;
    }

}
