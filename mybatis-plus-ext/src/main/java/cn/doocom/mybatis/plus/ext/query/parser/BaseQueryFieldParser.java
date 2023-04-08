package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;

public abstract class BaseQueryFieldParser implements QueryFieldParser {

    protected QueryColumnInfo extract(QueryColumn queryColumn) {
        return QueryColumnInfo.builder()
                .operation(queryColumn.value().getOperation())
                .column(queryColumn.column())
                .groupId(queryColumn.groupId())
                .logic(queryColumn.logic())
                .check(queryColumn.check().getExpression())
                .build();
    }

}
