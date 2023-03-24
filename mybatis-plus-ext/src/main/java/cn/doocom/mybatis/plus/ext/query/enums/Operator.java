package cn.doocom.mybatis.plus.ext.query.enums;

import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;
import cn.doocom.mybatis.plus.ext.query.function.BinaryOperation;

public enum Operator {

    // UnaryOperation
    EQ(((wrapper, column, value) -> wrapper.eq(column, value))),
    NE((wrapper, column, value) -> wrapper.ne(column, value)),
    GT((wrapper, column, value) -> wrapper.gt(column, value)),
    GE((wrapper, column, value) -> wrapper.ge(column, value)),
    LT((wrapper, column, value) -> wrapper.lt(column, value)),
    LE((wrapper, column, value) -> wrapper.le(column, value)),
    LIKE((wrapper, column, value) -> wrapper.like(column, value)),
    NOT_LIKE((wrapper, column, value) -> wrapper.notLike(column, value)),
    LIKE_LEFT((wrapper, column, value) -> wrapper.likeLeft(column, value)),
    LIKE_RIGHT((wrapper, column, value) -> wrapper.likeRight(column, value)),

    // BinaryOperation
    BETWEEN((BinaryOperation) (wrapper, column, value1, value2) -> wrapper.between(column, value1, value2)),
    NOT_BETWEEN((BinaryOperation) (wrapper, column, value1, value2) -> wrapper.notBetween(column, value1, value2)),

    // MultiOperation
    IN((wrapper, column, values) -> wrapper.in(column, values)),
    NOT_IN((wrapper, column, values) -> wrapper.notIn(column, values)),
    ;
    
    final BaseOperation baseOperation;

    Operator(BaseOperation baseOperation) {
        this.baseOperation = baseOperation;
    }

}
