package cn.doocom.mybatis.ext.query.enums;

public enum Operator {

    // Type.UNARY
    EQ(Type.UNARY),
    NE(Type.UNARY),
    GT(Type.UNARY),
    GE(Type.UNARY),
    LT(Type.UNARY),
    LE(Type.UNARY),
    LIKE(Type.UNARY),
    NOT_LIKE(Type.UNARY),
    LIKE_LEFT(Type.UNARY),
    LIKE_RIGHT(Type.UNARY),
    NOT_LIKE_LEFT(Type.UNARY),
    NOT_LIKE_RIGHT(Type.UNARY),

    // Type.BINARY
    BETWEEN(Type.BINARY),
    NOT_BETWEEN(Type.BINARY),

    // Type.MULTI
    IN(Type.MULTI),
    NOT_IN(Type.MULTI),
    ;

    final Type type;

    Operator(Type type) {
        this.type = type;
    }

    public enum Type {
        UNARY,
        BINARY,
        MULTI
    }

}
