package cn.doocom.common.util;

import cn.doocom.common.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationUtil {

    public static <T extends Annotation> List<T> getDeclaredAnnotationsByType(Class<?> clz, Class<T> annotation) {
        return getDeclaredAnnotationsByType(clz, annotation, false);
    }

    public static List<Class<?>> getAnnotatedClasses(Class<?> clz, Class<? extends Annotation> annotation) {
        return getAnnotatedClasses(clz, annotation, false);
    }

    public static List<Field> getAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation) {
        return getAnnotatedFields(clz, annotation, false);
    }

    public static <T extends Annotation> List<T> getDeclaredAnnotationsByType(Class<?> clz, Class<T> annotation, boolean includedSuperclasses) {
        List<T> result = new ArrayList<>();
        doGetDeclaredAnnotationsByType(clz, annotation, includedSuperclasses, result, getContainerAnnotation(annotation));
        return result;
    }

    public static List<Class<?>> getAnnotatedClasses(Class<?> clz, Class<? extends Annotation> annotation, boolean includedSuperclasses) {
        List<Class<?>> result = new ArrayList<>();
        doGetAnnotatedClasses(clz, annotation, includedSuperclasses, result, getContainerAnnotation(annotation));
        return result;
    }

    public static List<Field> getAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation, boolean includedSuperclasses) {
        List<Field> result = new ArrayList<>();
        doGetAnnotatedFields(clz, annotation, includedSuperclasses, result, getContainerAnnotation(annotation));
        return result;
    }

    @Nullable
    public static Class<? extends Annotation> getContainerAnnotation(Class<? extends Annotation> annotation) {
        if (annotation.isAnnotationPresent(Repeatable.class)) {
            return annotation.getAnnotation(Repeatable.class).value();
        }
        return null;
    }

    private static <T extends Annotation> void doGetDeclaredAnnotationsByType(Class<?> clz, Class<T> annotation,
                                                                              boolean includedSuperclasses, List<T> result,
                                                                              @Nullable Class<? extends Annotation> containerAnnotation) {
        Class<?> superclass;
        if (Object.class == clz || (superclass = clz.getSuperclass()) == null) {
            return ;
        }
        if (includedSuperclasses) {
            doGetDeclaredAnnotationsByType(superclass, annotation, true, result, containerAnnotation);
        }
        if (clz.isAnnotationPresent(annotation)
                || (containerAnnotation != null && clz.isAnnotationPresent(containerAnnotation))) {
            result.addAll(Arrays.asList(clz.getDeclaredAnnotationsByType(annotation)));
        }
    }

    private static void doGetAnnotatedClasses(Class<?> clz, Class<? extends Annotation> annotation,
                                              boolean includedSuperclasses, List<Class<?>> result,
                                              @Nullable Class<? extends Annotation> containerAnnotation) {
        Class<?> superclass;
        if (Object.class == clz || (superclass = clz.getSuperclass()) == null) {
            return ;
        }
        if (includedSuperclasses) {
            doGetAnnotatedClasses(superclass, annotation, true, result, containerAnnotation);
        }
        if (clz.isAnnotationPresent(annotation)
                || (containerAnnotation != null && clz.isAnnotationPresent(containerAnnotation))) {
            result.add(clz);
        }
    }

    private static void doGetAnnotatedFields(Class<?> clz, Class<? extends Annotation> annotation,
                                             boolean includedSuperclasses, List<Field> result,
                                             @Nullable Class<? extends Annotation> containerAnnotation) {
        Class<?> superclass;
        if (Object.class == clz || (superclass = clz.getSuperclass()) == null) {
            return ;
        }
        if (includedSuperclasses) {
            doGetAnnotatedFields(superclass, annotation, true, result, containerAnnotation);
        }
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(annotation)
                    || (containerAnnotation != null && field.isAnnotationPresent(containerAnnotation))) {
                result.add(field);
            }
        }
    }

}
