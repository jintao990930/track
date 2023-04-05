package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;

public abstract class BaseQueryFieldParser implements QueryFieldParser {

    protected QueryColumnInfo extract(QueryColumn queryColumn) {
        QueryColumnInfo result = new QueryColumnInfo();
        result.setOperation(queryColumn.value().getOperation());
        result.setColumn(queryColumn.column());
        result.setGroupId(queryColumn.groupId());
        result.setLogic(queryColumn.logic());
        result.setCheck(queryColumn.check().getExpression());
        return result;
    }

}
