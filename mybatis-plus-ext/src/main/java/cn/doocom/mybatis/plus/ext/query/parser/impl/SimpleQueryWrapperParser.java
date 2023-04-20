package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.struct.QueryTree;
import cn.doocom.mybatis.plus.ext.query.parser.BaseQueryWrapperParser;
import cn.doocom.mybatis.plus.ext.query.parser.QueryClassParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SimpleQueryWrapperParser extends BaseQueryWrapperParser {

    public SimpleQueryWrapperParser() {
        super();
    }

    public SimpleQueryWrapperParser(QueryClassParser queryClassParser) {
        super(queryClassParser);
    }

    @Override
    public <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass, boolean includeInheritedFields) {
        QueryClass queryClass = parseClass(obj.getClass(), includeInheritedFields);
        QueryTree queryTree = new QueryTree(queryClass);
        return parse(obj, entityClass, queryTree);
    }

}
