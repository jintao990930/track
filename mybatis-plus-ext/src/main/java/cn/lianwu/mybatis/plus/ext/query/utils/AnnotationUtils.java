package cn.lianwu.mybatis.plus.ext.query.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationUtils {

    public static List<Field> getAnnotatedFields(Class<?> clz, boolean includedSuperclasses, Class<? extends Annotation>... annotation) {
        List<Field> result = new ArrayList<>();
        doGetAnnotatedFields(result, clz, includedSuperclasses, annotation);
        return result;
    }

    private static void doGetAnnotatedFields(List<Field> result, Class<?> clz, boolean includedSuperclasses, Class<? extends Annotation>... annotation) {
        Class<?> superclass;
        if (Object.class == clz || (superclass = clz.getSuperclass()) == null) {
            return ;
        }
        if (includedSuperclasses) {
            doGetAnnotatedFields(result, superclass, true, annotation);
        }

        Field[] declaredFields = clz.getDeclaredFields();
        result.addAll(Arrays.stream(declaredFields)
                .filter(e -> Arrays.stream(annotation).anyMatch(e::isAnnotationPresent))
                .collect(Collectors.toList()));

    }

}
