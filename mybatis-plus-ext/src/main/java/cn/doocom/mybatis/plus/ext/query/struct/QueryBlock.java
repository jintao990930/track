package cn.doocom.mybatis.plus.ext.query.struct;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;

public class QueryBlock {

    private final BaseOperation operation;
    private final String column;
    private final Logic innerLogic;

    public QueryBlock(BaseOperation operation, String column, Logic innerLogic) {
        this.operation = operation;
        this.column = column;
        this.innerLogic = innerLogic;
    }

    public BaseOperation getOperation() {
        return operation;
    }

    public String getColumn() {
        return column;
    }

    public Logic getInnerLogic() {
        return innerLogic;
    }

    static QueryBlock convert(QueryColumn queryColumn) {
        return new QueryBlock(queryColumn.value().getOperation(),
                queryColumn.column(),
                queryColumn.logic());
    }

}
