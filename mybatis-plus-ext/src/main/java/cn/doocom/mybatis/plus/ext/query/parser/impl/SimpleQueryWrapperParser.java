package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryOption;
import cn.doocom.mybatis.plus.ext.query.model.QueryTree;
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
    public <T> QueryWrapper<T> parseWrapper(Object obj, boolean includeInheritedFields, @Nullable QueryOption<T> option) {
        QueryClass queryClass = parseClass(obj.getClass(), includeInheritedFields);
        QueryTree queryTree = new QueryTree(queryClass);
        return parse(obj, queryTree, option);
    }

}
