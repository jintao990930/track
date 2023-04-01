package cn.doocom.mybatis.plus.ext.query.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public enum Check {

    AUTO(obj -> Check.notEmpty(obj, false)),

    NOT_NULL(Objects::nonNull),

    NOT_BLANK(Check::notBlank),

    NOT_EMPTY(obj -> Check.notEmpty(obj, true)),
    ;

    final Function<Object, Boolean> expression;

    Check(Function<Object, Boolean> expression) {
        this.expression = expression;
    }

    public Function<Object, Boolean> getExpression() {
        return expression;
    }

    private static boolean notBlank(Object obj) {
        if (obj == null)    return false;
        if (obj instanceof CharSequence)
            return StringUtils.isNotBlank((CharSequence) obj);
        throw new IllegalArgumentException("The argument should be of type CharSequence;");
    }

    private static boolean notEmpty(Object obj, boolean elseThrowable) {
        if (obj == null)    return false;
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() > 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) > 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() > 0;
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() > 0;
        }
        if (elseThrowable)
            throw new IllegalArgumentException("The argument should be of type CharSequence, Array, Collection, or Map;");
        return true;
    }

}
