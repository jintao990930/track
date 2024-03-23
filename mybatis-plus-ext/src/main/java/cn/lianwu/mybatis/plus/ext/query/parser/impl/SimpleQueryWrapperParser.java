package cn.lianwu.mybatis.plus.ext.query.parser.impl;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.model.QueryTree;
import cn.lianwu.mybatis.plus.ext.query.parser.QueryClassParser;
import cn.lianwu.mybatis.plus.ext.query.parser.BaseQueryWrapperParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SimpleQueryWrapperParser extends BaseQueryWrapperParser {

    public SimpleQueryWrapperParser() {
        super();
    }

    public SimpleQueryWrapperParser(QueryClassParser queryClassParser) {
        super(queryClassParser);
    }

    @Override
    public <T> QueryWrapper<T> parseWrapper(Object obj, boolean includedSuperclasses, QueryOption<T> option) {
        QueryTree queryTree = new QueryTree(parseClass(obj.getClass(), includedSuperclasses));
        return parse(obj, queryTree, option);
    }

}
