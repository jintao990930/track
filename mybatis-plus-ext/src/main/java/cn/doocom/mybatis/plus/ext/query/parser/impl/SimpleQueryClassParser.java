package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.parser.QueryClassParser;

public class SimpleQueryClassParser implements QueryClassParser {

    @Override
    public <T> QueryClass<T> parseClass(Class<T> clz) {
        return null;
    }

}
