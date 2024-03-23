package cn.lianwu.mybatis.plus.ext.query.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Function;

public enum Check {

    NEVER(o -> true),

    NOT_EMPTY(Check::isNotEmpty),

    ;

    final Function<Object, Boolean> expression;

    Check(Function<Object, Boolean> expression) {
        this.expression = expression;
    }

    public Function<Object, Boolean> getExpression() {
        return expression;
    }

    private static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof CharSequence) {
            return StringUtils.isNotBlank((CharSequence) obj);
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) > 0;
        }
        if (obj instanceof Collection) {
            return !((Collection<?>) obj).isEmpty();
        }
        return true;
    }

}
