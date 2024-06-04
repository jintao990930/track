package cn.lianwu.mybatis.plus.ext.query.model;

import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.function.BaseOperation;

import java.util.function.Function;

public class PreparedQueryStatement {

    private final Function<Object, Boolean> validExpression;

    private final String groupId;

    private final Logic logic;

    private final String column;

    private final BaseOperation operation;

    public PreparedQueryStatement(Function<Object, Boolean> validExpression, String groupId, Logic logic, String column, BaseOperation operation) {
        this.validExpression = validExpression;
        this.groupId = groupId;
        this.logic = logic;
        this.column = column;
        this.operation = operation;
    }

    public Function<Object, Boolean> getValidExpression() {
        return validExpression;
    }

    public String getGroupId() {
        return groupId;
    }

    public Logic getLogic() {
        return logic;
    }

    public String getColumn() {
        return column;
    }

    public BaseOperation getOperation() {
        return operation;
    }

}
