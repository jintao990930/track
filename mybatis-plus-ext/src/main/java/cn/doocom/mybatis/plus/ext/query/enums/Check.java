package cn.doocom.mybatis.plus.ext.query.enums;

import cn.doocom.util.ObjectUtil;

import java.util.function.Function;

public enum Check {

    /**
     * for Object
     */
    NOT_NULL(ObjectUtil::notNull),
    /**
     * for String
     */
    NOT_BLANK(ObjectUtil::notBlank),
    /**
     * for String, Array, Collection, Map
     */
    NOT_EMPTY(ObjectUtil::notEmpty),
    ;

    final Function<?, Boolean> expression;

    Check(Function<?, Boolean> expression) {
        this.expression = expression;
    }

    public Function<?, Boolean> getExpression() {
        return expression;
    }

}
