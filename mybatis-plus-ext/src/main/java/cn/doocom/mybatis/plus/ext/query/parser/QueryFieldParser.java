package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryField;

import java.lang.reflect.Field;

public interface QueryFieldParser {

    QueryField parseField(Field field);

}
