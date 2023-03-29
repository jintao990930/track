package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.parser.AbstractQueryWrapperParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SimpleQueryWrapperParser extends AbstractQueryWrapperParser {

    @Override
    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, boolean includeSuperclass) {
        return null;
    }

}
