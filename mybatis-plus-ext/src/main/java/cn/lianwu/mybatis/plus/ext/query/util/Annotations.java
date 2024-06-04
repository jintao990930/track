package cn.lianwu.mybatis.plus.ext.query.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Annotations {

    @SafeVarargs
    public static List<Field> listAnyAnnotatedFields(Class<?> clz, boolean includeInheritedFields, Class<? extends Annotation>... annotation) {

        return Optional.ofNullable(clz)
                .filter(c -> c != Object.class)
                .map(c -> {
                    List<Field> annotatedFields = Arrays.stream(c.getDeclaredFields())
                            .filter(e -> Arrays.stream(annotation).anyMatch(e::isAnnotationPresent))
                            .collect(Collectors.toList());
                    if (includeInheritedFields) {
                        annotatedFields.addAll(listAnyAnnotatedFields(c.getSuperclass(), true, annotation));
                    }
                    return annotatedFields;
                }).orElseGet(ArrayList::new);
    }

}
