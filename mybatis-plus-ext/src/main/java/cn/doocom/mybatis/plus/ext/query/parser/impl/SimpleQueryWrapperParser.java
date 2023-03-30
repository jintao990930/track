package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.parser.AbstractQueryWrapperParser;
import cn.doocom.mybatis.plus.ext.query.parser.QueryClassParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

public class SimpleQueryWrapperParser extends AbstractQueryWrapperParser {

    public SimpleQueryWrapperParser() {
        super();
    }

    public SimpleQueryWrapperParser(QueryClassParser queryClassParser) {
        super(queryClassParser);
    }

    @Override
    public <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, boolean includeSuperclass) {
        QueryWrapper<T> result = Wrappers.query();

        return result;
    }

}
