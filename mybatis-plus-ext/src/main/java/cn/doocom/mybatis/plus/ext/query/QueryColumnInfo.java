package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Check;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;
import cn.doocom.util.ObjectUtil;

import java.util.Objects;
import java.util.function.Function;

public final class QueryColumnInfo {

    private final BaseOperation operation;
    private final String column;
    private final String groupId;
    private final Logic logic;
    private final Function<Object, Boolean> check;

    private QueryColumnInfo(BaseOperation operation,
                           String column,
                           String groupId,
                           Logic logic,
                           Function<Object, Boolean> check) {
        this.operation = operation;
        this.column = column;
        this.groupId = groupId;
        this.logic = logic;
        this.check = check;
    }

    public static QueryColumnInfoBuilder builder() {
        return new QueryColumnInfoBuilder();
    }

    public BaseOperation getOperation() {
        return operation;
    }

    public String getColumn() {
        return column;
    }

    public String getGroupId() {
        return groupId;
    }

    public Logic getLogic() {
        return logic;
    }

    public Function<Object, Boolean> getCheck() {
        return check;
    }

    public static class QueryColumnInfoBuilder {

        private BaseOperation operation;
        private String column;
        private String groupId;
        private Logic logic;
        private Function<Object, Boolean> check;

        private QueryColumnInfoBuilder() { }

        public QueryColumnInfo build() {
            operation = ObjectUtil.getOr(operation, Operator.EQ.getOperation());
            column = ObjectUtil.getOr(column, QueryConst.HUMP_2_UNDER_LINE_FLAG);
            groupId = ObjectUtil.getOr(groupId, QueryConst.MAIN_GROUP_ID);
            logic = ObjectUtil.getOr(logic, Logic.AND);
            check = ObjectUtil.getOr(check, Check.AUTO.getExpression());
            return new QueryColumnInfo(operation, column, groupId, logic, check);
        }

        public QueryColumnInfoBuilder operation(BaseOperation operation) {
            assert Objects.nonNull(operation);
            this.operation = operation;
            return this;
        }

        public QueryColumnInfoBuilder column(String column) {
            assert Objects.nonNull(column);
            this.column = column;
            return this;
        }

        public QueryColumnInfoBuilder groupId(String groupId) {
            assert Objects.nonNull(groupId);
            this.groupId = groupId;
            return this;
        }

        public QueryColumnInfoBuilder logic(Logic logic) {
            assert Objects.nonNull(logic);
            this.logic = logic;
            return this;
        }

        public QueryColumnInfoBuilder check(Function<Object, Boolean> check) {
            assert Objects.nonNull(check);
            this.check = check;
            return this;
        }

    }

}
