package cn.doocom.util;

import com.sun.istack.internal.Nullable;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtil {

    public static List<Field> getAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation) {
        return getAnnotatedFields(clz, annotation, false);
    }

    public static List<Field> getAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation, boolean includeSuperclass) {
        List<Field> result = new ArrayList<>();
        doGetAnnotatedFieldsIncludeSuperclass(clz, annotation, getContainerAnnotation(annotation), result, includeSuperclass);
        return result;
    }

    public static Class<? extends Annotation> getContainerAnnotation(Class<? extends Annotation> annotation) {
        if (annotation.isAnnotationPresent(Repeatable.class)) {
            return annotation.getAnnotation(Repeatable.class).value();
        }
        return null;
    }

    private static void doGetAnnotatedFieldsIncludeSuperclass(Class<?> clz, Class<? extends Annotation> annotation,
                                                              @Nullable Class<? extends Annotation> containerAnnotation,
                                                              List<Field> result, boolean includeSuperclass) {
        Class<?> superclass;
        if (Object.class == clz || (superclass = clz.getSuperclass()) == null)
            return ;
        if (includeSuperclass) 
            doGetAnnotatedFieldsIncludeSuperclass(superclass, annotation, containerAnnotation, result, true);
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(annotation)) {
                result.add(field);
            } else if (containerAnnotation != null && field.isAnnotationPresent(containerAnnotation)) {
                result.add(field);
            }
        }
    }

}
