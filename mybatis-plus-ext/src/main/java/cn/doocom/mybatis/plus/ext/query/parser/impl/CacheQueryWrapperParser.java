package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.parser.BaseQueryWrapperParser;
import cn.doocom.mybatis.plus.ext.query.parser.QueryClassParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class CacheQueryWrapperParser extends BaseQueryWrapperParser {

    public CacheQueryWrapperParser() {
        super();
    }

    public CacheQueryWrapperParser(QueryClassParser queryClassParser) {
        super(queryClassParser);
    }

    @Override
    public <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass, boolean includeInheritedFields) {
        return null;
    }

}
