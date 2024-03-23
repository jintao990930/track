package cn.lianwu.mybatis.plus.ext.query.model;

import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.consts.QueryConsts;
import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.function.BaseOperation;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class QueryBlock {

    private final Logic logic;

    private final String column;

    private final BaseOperation operation;

    public QueryBlock(Logic logic, String column, BaseOperation operation) {
        this.logic = logic;
        this.column = column;
        this.operation = operation;
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

    static protected QueryBlock assemble(Field field, QueryColumn qc) {
        String column = qc.column();
        if (QueryConsts.HUMP_2_UNDER_LINE_FLAG.equals(column)) {
            column = StringUtils.camelToUnderline(field.getName());
        }
        return new QueryBlock(qc.logic(), column, qc.value().getOperation());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryBlock that = (QueryBlock) o;
        return logic == that.logic && Objects.equals(column, that.column) && Objects.equals(operation, that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logic, column, operation);
    }

}
