package cn.edu.zhku.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

public class AnnotationUtil {

    private static final Set<String> packageInclude;
    private static final Set<String> packageExclude;
    private static final Set<Annotation> annotationInclude;
    private static final Set<Annotation> annotationExclude;

    static {
        packageInclude = new HashSet<>();
        packageExclude = new HashSet<>();
        annotationInclude = new HashSet<>();
        annotationExclude = new HashSet<>();

        packageExclude.add("java.lang.annotation");
    }

    public static boolean isAnnotationPresent(AnnotatedElement annotatedElement,
                                              Class<? extends Annotation> annotationClass) {
        if (annotatedElement.isAnnotationPresent(annotationClass)) {
            return true;
        }
        Annotation[] annoArray = annotatedElement.getAnnotations();
        for (Annotation anno : annoArray) {
            if (isAnnotationFiltered(anno)) {
                continue;
            }
            if (isAnnotationPresent(anno.annotationType(), annotationClass)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Annotation> T getAnnotation(AnnotatedElement annotatedElement,
                                                         Class<T> annotationClass) {
        if (annotatedElement.isAnnotationPresent(annotationClass)) {
            return doGetAnnotation(annotatedElement, annotatedElement, annotationClass);
        }
        Annotation[] annoArray = annotatedElement.getAnnotations();
        for (Annotation anno : annoArray) {
            if (isAnnotationPresent(anno.annotationType(), annotationClass)) {
                return doGetAnnotation(annotatedElement, anno.annotationType(), annotationClass);
            }
        }
        return null;
    }

    private static <T extends Annotation> T doGetAnnotation(AnnotatedElement sourceElement,
                                                            AnnotatedElement targetElement,
                                                            Class<T> annotationClass) {
        if (sourceElement.equals(targetElement)) {
            return sourceElement.getAnnotation(annotationClass);
        }
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[] {annotationClass},
                ((proxy, method, args) -> {
                    Class<Annotation> targetAnnoClass = ((Class) targetElement).asSubclass(Annotation.class);
                    Annotation targetAnno = sourceElement.getAnnotation(targetAnnoClass);
                    Method targetMethod = targetAnnoClass.getMethod(method.getName());
                    return targetMethod.invoke(targetAnno);
                }));
    }

    private static boolean isAnnotationFiltered(Annotation annotation) {
        if (packageInclude.contains(annotation.annotationType().getPackage().getName())) {
            return false;
        }
        if (packageExclude.contains(annotation.annotationType().getPackage().getName())) {
            return true;
        }
        if (annotationInclude.contains(annotation)) {
            return false;
        }
        if (annotationExclude.contains(annotation)) {
            return true;
        }
        return false;
    }

}