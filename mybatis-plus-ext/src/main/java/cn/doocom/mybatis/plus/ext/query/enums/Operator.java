package cn.doocom.mybatis.plus.ext.query.enums;

import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;
import cn.doocom.mybatis.plus.ext.query.function.BinaryOperation;

public enum Operator {

    EQ(Type.UNARY, ((wrapper, column, value) -> wrapper.eq(column, value))),
    NE(Type.UNARY, (wrapper, column, value) -> wrapper.ne(column, value)),
    GT(Type.UNARY, (wrapper, column, value) -> wrapper.gt(column, value)),
    GE(Type.UNARY, (wrapper, column, value) -> wrapper.ge(column, value)),
    LT(Type.UNARY, (wrapper, column, value) -> wrapper.lt(column, value)),
    LE(Type.UNARY, (wrapper, column, value) -> wrapper.le(column, value)),
    LIKE(Type.UNARY, (wrapper, column, value) -> wrapper.like(column, value)),
    NOT_LIKE(Type.UNARY, (wrapper, column, value) -> wrapper.notLike(column, value)),
    LIKE_LEFT(Type.UNARY, (wrapper, column, value) -> wrapper.likeLeft(column, value)),
    LIKE_RIGHT(Type.UNARY, (wrapper, column, value) -> wrapper.likeRight(column, value)),

    BETWEEN(Type.BINARY, (BinaryOperation) (wrapper, column, value1, value2) -> wrapper.between(column, value1, value2)),
    NOT_BETWEEN(Type.BINARY, (BinaryOperation) (wrapper, column, value1, value2) -> wrapper.notBetween(column, value1, value2)),

    IN(Type.MULTI, (wrapper, column, values) -> wrapper.in(column, values)),
    NOT_IN(Type.MULTI, (wrapper, column, values) -> wrapper.notIn(column, values)),
    ;
    
    final BaseOperation operation;
    final Type type;

    Operator(Type type, BaseOperation operation) {
        this.type = type;
        this.operation = operation;
    }

    public Type getType() {
        return type;
    }

    public BaseOperation getOperation() {
        return operation;
    }

    public enum Type {
        UNARY,
        BINARY,
        MULTI,
    }

}
