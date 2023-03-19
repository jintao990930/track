package cn.doocom.mybatis.ext.enums;

import java.util.Objects;
import java.util.function.Function;

public enum Check {

    NONE(o -> true),
    /**
     * for Object
     */
    NOT_NULL(Objects::nonNull),
    /**
     * for String
     */
    // TODO
    NOT_BLANK(o -> true),
    /**
     * for String, Array, Collection, Map
     */
    // TODO
    NOT_EMPTY(o -> true),
    ;

    final Function<?, Boolean> expression;

    Check(Function<?, Boolean> expression) {
        this.expression = expression;
    }

}
