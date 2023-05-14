package cn.doocom.mybatis.plus.ext.query.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Function;

public enum Check {

    NONE(o -> true),
    AUTO(Check::validate),

    ;

    final Function<Object, Boolean> expression;

    Check(Function<Object, Boolean> expression) {
        this.expression = expression;
    }

    public Function<Object, Boolean> getExpression() {
        return expression;
    }

    private static boolean validate(Object obj) {
        if (obj == null)    return false;
        if (obj instanceof CharSequence) {
            return StringUtils.isNotBlank((CharSequence) obj);
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) > 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() > 0;
        }
        return true;
    }

}
