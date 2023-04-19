package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;

public class WhereBlock {

    private final BaseOperation operation;
    private final String column;
    private final Logic innerLogic;

    public WhereBlock(BaseOperation operation, String column, Logic innerLogic) {
        this.operation = operation;
        this.column = column;
        this.innerLogic = innerLogic;
    }

    public static WhereBlock convert(QueryColumn queryColumn) {
        return new WhereBlock(queryColumn.value().getOperation(),
                queryColumn.column(),
                queryColumn.logic());
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

}
