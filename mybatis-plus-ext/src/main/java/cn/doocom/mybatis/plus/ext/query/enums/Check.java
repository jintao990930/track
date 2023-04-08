package cn.doocom.mybatis.plus.ext.query.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public enum Check {

    AUTO(Check::auto),

    NOT_NULL(Objects::nonNull),

    NOT_BLANK(Check::notBlank),

    NOT_EMPTY(Check::notEmpty),
    ;

    final Function<Object, Boolean> expression;

    Check(Function<Object, Boolean> expression) {
        this.expression = expression;
    }

    public Function<Object, Boolean> getExpression() {
        return expression;
    }

    private static boolean auto(Object obj) {
        if (obj == null)    return false;
        if (obj instanceof CharSequence) {
            return StringUtils.isNotBlank((CharSequence) obj);
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) > 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() > 0;
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() > 0;
        }
        return true;
    }

    private static boolean notBlank(Object obj) {
        if (obj == null)    return false;
        if (obj instanceof CharSequence)
            return StringUtils.isNotBlank((CharSequence) obj);
        throw new IllegalArgumentException("The argument should be of type CharSequence.");
    }

    private static boolean notEmpty(Object obj) {
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
        throw new IllegalArgumentException("The argument should be of type CharSequence, Array, Collection, or Map.");
    }

}
