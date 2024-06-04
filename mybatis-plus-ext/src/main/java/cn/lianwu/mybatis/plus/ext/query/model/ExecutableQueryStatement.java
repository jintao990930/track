package cn.lianwu.mybatis.plus.ext.query.model;

import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.function.BaseOperation;

public class ExecutableQueryStatement {

    private final String groupId;

    private final Logic logic;

    private final String column;

    private final BaseOperation operation;

    private final Object value;

    private ExecutableQueryStatement(String groupId, Logic logic, String column, BaseOperation operation, Object value) {
        this.groupId = groupId;
        this.logic = logic;
        this.column = column;
        this.operation = operation;
        this.value = value;
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

    public Object getValue() {
        return value;
    }

    public static ExecutableQueryStatement assemble(PreparedQueryStatement preparedStatement, Object value) {
        return new ExecutableQueryStatement(preparedStatement.getGroupId(), preparedStatement.getLogic(),
                preparedStatement.getColumn(), preparedStatement.getOperation(), value);
    }

}
