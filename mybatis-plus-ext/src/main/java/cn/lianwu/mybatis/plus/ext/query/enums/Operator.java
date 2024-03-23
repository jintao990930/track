package cn.lianwu.mybatis.plus.ext.query.enums;

import cn.lianwu.mybatis.plus.ext.query.function.BaseOperation;
import cn.lianwu.mybatis.plus.ext.query.function.MultiOperation;
import cn.lianwu.mybatis.plus.ext.query.function.UnaryOperation;
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;

public enum Operator {

    EQ((UnaryOperation) Compare::eq),

    NE((UnaryOperation) Compare::ne),

    GT((UnaryOperation) Compare::gt),

    GE((UnaryOperation) Compare::ge),

    LT((UnaryOperation) Compare::lt),

    LE((UnaryOperation) Compare::le),

    LIKE((UnaryOperation) Compare::like),

    NOT_LIKE((UnaryOperation) Compare::notLike),

    LIKE_LEFT((UnaryOperation) Compare::likeLeft),

    LIKE_RIGHT((UnaryOperation) Compare::likeRight),

    IN((MultiOperation) Func::in),

    NOT_IN((MultiOperation) Func::notIn),

    ;
    
    final BaseOperation operation;

    Operator(BaseOperation operation) {
        this.operation = operation;
    }

    public BaseOperation getOperation() {
        return operation;
    }

}
