package cn.doocom.util;

public class ObjectUtil {

    public static <T> T getOr(T nullableValue, T defaultValue) {
        if (nullableValue == null) {
            return defaultValue;
        }
        return nullableValue;
    }

}
