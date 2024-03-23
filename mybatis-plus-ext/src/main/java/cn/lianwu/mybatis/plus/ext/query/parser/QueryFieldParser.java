package cn.lianwu.mybatis.plus.ext.query.parser;

import cn.lianwu.mybatis.plus.ext.query.QueryField;

import java.lang.reflect.Field;

public interface QueryFieldParser {

    QueryField parseField(Field field);

}
