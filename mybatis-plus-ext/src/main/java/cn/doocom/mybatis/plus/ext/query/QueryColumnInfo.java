package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;

import java.util.function.Function;

public class QueryColumnInfo {

    private BaseOperation operation;
    private String column;
    private String groupId;
    private Logic logic;
    private Function<Object, Boolean> check;

    public BaseOperation getOperation() {
        return operation;
    }

    public void setOperation(BaseOperation operation) {
        this.operation = operation;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public Function<Object, Boolean> getCheck() {
        return check;
    }

    public void setCheck(Function<Object, Boolean> check) {
        this.check = check;
    }

}
