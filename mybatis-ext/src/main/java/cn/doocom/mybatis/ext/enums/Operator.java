package cn.doocom.mybatis.ext.enums;

public enum Operator {

    // unary
    EQ,
    NE,
    GT,
    GE,
    LT,
    LE,
    LIKE,
    NOT_LIKE,
    LIKE_LEFT,
    LIKE_RIGHT,
    NOT_LIKE_LEFT,
    NOT_LIKE_RIGHT,

    // binary
    BETWEEN,
    NOT_BETWEEN,

    // multi
    IN,
    NOT_IN,

}
