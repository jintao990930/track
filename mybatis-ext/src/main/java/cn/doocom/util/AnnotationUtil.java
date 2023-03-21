package cn.doocom.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtil {

    public static List<Field> getAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation) {
        return getAnnotatedFields(clz, annotation, false);
    }

    public static List<Field> getAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation, boolean includeSuperclass) {
        List<Field> result = new ArrayList<>();
        if (includeSuperclass)
            doGetAnnotatedFieldsIncludeSuperclass(clz, annotation, result);
        else
            doGetAnnotatedFields(clz, annotation, result);
        return result;
    }

    private static void doGetAnnotatedFieldsIncludeSuperclass(Class<?> clz, Class<? extends Annotation> annotation, List<Field> result) {
        Class<?> superclass;
        if (Object.class == clz || (superclass = clz.getSuperclass()) == null)
            return ;
        doGetAnnotatedFieldsIncludeSuperclass(superclass, annotation, result);
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(annotation)) {
                result.add(field);
            }
        }
    }

    private static void doGetAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation, List<Field> result) {
        if (Object.class == clz || clz.getSuperclass() == null)
            return ;
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(annotation)) {
                result.add(field);
            }
        }
    }

}
