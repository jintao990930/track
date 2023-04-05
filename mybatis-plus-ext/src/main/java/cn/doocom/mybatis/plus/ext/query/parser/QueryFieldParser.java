package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;

import java.lang.reflect.Field;

public interface QueryFieldParser {

    QueryField parseField(Field field);

    QueryColumnInfo extract(QueryColumn queryColumn);

}
