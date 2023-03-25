package cn.doocom.util;

import java.util.Collection;
import java.util.Map;

public class ObjectUtil {

    public static boolean notNull(Object obj) {
        return obj != null;
    }

    public static boolean notBlank(Object obj, boolean throwable) {
        if (obj == null)    return false;
        if (obj instanceof String)
            return ((String) obj).trim().length() > 0;
        if (throwable)
            throw new IllegalArgumentException("The argument should be of type \"String\";");
        else
            return false;
    }

    public static boolean notEmpty(Object obj, boolean throwable) {
        if (obj == null)    return false;
        if (obj instanceof String) {
            return ((String) obj).length() > 0;
        } else if (obj.getClass().isArray()) {
            return ((Object[]) obj).length > 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() > 0;
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() > 0;
        }
        if (throwable)
            throw new IllegalArgumentException("The argument should be of type \"String\", \"Array\", \"Collection\", or \"Map\";");
        else
            return false;
    }

}
