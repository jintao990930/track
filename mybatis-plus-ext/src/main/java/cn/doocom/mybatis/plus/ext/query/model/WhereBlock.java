package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;

public class WhereBlock {

    private final BaseOperation operation;
    private final Operator.Type operatorType;
    private final String column;
    private final Logic innerLogic;

    public WhereBlock(BaseOperation operation, Operator.Type operatorType,
                      String column, Logic innerLogic) {
        this.operation = operation;
        this.operatorType = operatorType;
        this.column = column;
        this.innerLogic = innerLogic;
    }

    public static WhereBlock convert(QueryColumn queryColumn) {
        return new WhereBlock(queryColumn.value().getOperation(),
                queryColumn.value().getType(),
                queryColumn.column(),
                queryColumn.logic());
    }

    public BaseOperation getOperation() {
        return operation;
    }

    public Operator.Type getOperatorType() {
        return operatorType;
    }

    public String getColumn() {
        return column;
    }

    public Logic getInnerLogic() {
        return innerLogic;
    }

}
