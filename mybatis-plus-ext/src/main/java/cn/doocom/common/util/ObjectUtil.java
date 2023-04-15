package cn.doocom.common.util;

import cn.doocom.common.annotation.Nullable;

import java.util.Objects;

public class ObjectUtil {

    @Nullable
    public static <T> T getOr(@Nullable T nullableValue, @Nullable T defaultValue) {
        return Objects.isNull(nullableValue) ? defaultValue : nullableValue;
    }

}
