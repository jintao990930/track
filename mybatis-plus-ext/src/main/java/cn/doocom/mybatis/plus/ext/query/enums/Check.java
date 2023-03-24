package cn.doocom.mybatis.plus.ext.query.enums;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public enum Check {

    NONE(o -> true),
    /**
     * for Object
     */
    NOT_NULL(Check::notNull),
    /**
     * for String
     */
    NOT_BLANK(Check::notBlank),
    /**
     * for String, Array, Collection, Map
     */
    NOT_EMPTY(Check::notEmpty),
    ;

    final Function<?, Boolean> expression;

    Check(Function<?, Boolean> expression) {
        this.expression = expression;
    }

    public Function<?, Boolean> getExpression() {
        return expression;
    }

    private static Boolean notNull(Object obj) {
        return obj != null;
    }

    private static Boolean notBlank(Object obj) {
        if (obj instanceof String) {
            return ((String) obj).trim().length() > 0;
        }
        throw new IllegalArgumentException("The argument should be of type \"String\";");
    }

    private static Boolean notEmpty(Object obj) {
        if (obj instanceof String) {
            return ((String) obj).length() > 0;
        } else if (obj.getClass().isArray()) {
            return ((Object[]) obj).length > 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() > 0;
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() > 0;
        }
        throw new IllegalArgumentException("The argument should be of type \"String\", \"Array\", \"Collection\", or \"Map\";");
    }

}
