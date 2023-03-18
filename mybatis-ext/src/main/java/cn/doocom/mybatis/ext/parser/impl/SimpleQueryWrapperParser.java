package cn.doocom.mybatis.ext.parser.impl;

import cn.doocom.mybatis.ext.QueryWrapperBean;
import cn.doocom.mybatis.ext.parser.QueryWrapperParser;

public class SimpleQueryWrapperParser implements QueryWrapperParser {

    @Override
    public <T> QueryWrapperBean<T> parseClass(Class<?> clz) {
        return null;
    }

}
