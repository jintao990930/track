package cn.doocom.mybatis.plus.ext.query.enums;

import cn.doocom.util.ObjectUtil;

import java.util.function.Function;

public enum Check {

    NONE(o -> true),
    /**
     * default
     */
    AUTO(o -> ObjectUtil.notBlank(o, false) ||
            ObjectUtil.notEmpty(o, false) ||
            ObjectUtil.notNull(o)),
    /**
     * for Object
     */
    NOT_NULL(ObjectUtil::notNull),
    /**
     * for String
     */
    NOT_BLANK(o -> ObjectUtil.notBlank(o, true)),
    /**
     * for String, Array, Collection, Map
     */
    NOT_EMPTY(o -> ObjectUtil.notEmpty(o, true)),
    ;

    final Function<?, Boolean> expression;

    Check(Function<?, Boolean> expression) {
        this.expression = expression;
    }

    public Function<?, Boolean> getExpression() {
        return expression;
    }

}
