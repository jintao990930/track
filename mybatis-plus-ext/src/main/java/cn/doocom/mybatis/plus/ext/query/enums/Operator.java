package cn.doocom.mybatis.plus.ext.query.enums;

import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;
import cn.doocom.mybatis.plus.ext.query.function.BinaryOperation;
import cn.doocom.mybatis.plus.ext.query.function.UnaryOperation;
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;

public enum Operator {

    EQ(Type.UNARY, (UnaryOperation) Compare::eq),
    NE(Type.UNARY, (UnaryOperation) Compare::ne),
    GT(Type.UNARY, (UnaryOperation) Compare::gt),
    GE(Type.UNARY, (UnaryOperation) Compare::ge),
    LT(Type.UNARY, (UnaryOperation) Compare::lt),
    LE(Type.UNARY, (UnaryOperation) Compare::le),
    LIKE(Type.UNARY, (UnaryOperation) Compare::like),
    NOT_LIKE(Type.UNARY, (UnaryOperation) Compare::notLike),
    LIKE_LEFT(Type.UNARY, (UnaryOperation) Compare::likeLeft),
    LIKE_RIGHT(Type.UNARY, (UnaryOperation) Compare::likeRight),

    BETWEEN(Type.BINARY, (BinaryOperation) Compare::between),
    NOT_BETWEEN(Type.BINARY, (BinaryOperation) Compare::notBetween),

    IN(Type.MULTI, Func::in),
    NOT_IN(Type.MULTI, Func::notIn),
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
