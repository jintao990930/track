package cn.lianwu.mybatis.plus.query.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字段获取工具，支持字段缓存
 *
 * @author LianWu
 */
public class FieldGetters {

    /**
     * 是否启用字段缓存，默认启用
     */
    private static boolean fieldsCacheEnabled = true;

    /**
     * 字段缓存表
     */
    private static final Map<Class<?>, List<Field>> fieldsCache = new ConcurrentHashMap<>(32);

    /**
     * 获取类的所有字段，不包含继承字段的获取
     *
     * @param target 目标类
     * @return 类的所有字段（不包含继承字段）
     */
    public static List<Field> getDeclaredFields(Class<?> target) {
        return getDeclaredFields(target, false);
    }

    /**
     * 获取类的所有字段，支持继承字段的获取
     *
     * @param target 目标类
     * @param includeInheritedFields 是否包含从父类继承的字段
     * @return 类的所有字段，当{@code includeInheritedFields}为{@code true}时，结果中包含继承字段
     */
    public static List<Field> getDeclaredFields(Class<?> target, boolean includeInheritedFields) {
        if (fieldsCacheEnabled) {
            if (includeInheritedFields) {
                List<Field> result = new ArrayList<>();
                while (target != null && target != Object.class) {
                    result.addAll(fieldsCache.computeIfAbsent(target, Reflections::getDeclaredFields));
                    target = target.getSuperclass();
                }
                return result;
            }
            return fieldsCache.computeIfAbsent(target, Reflections::getDeclaredFields);
        } else {
            if (includeInheritedFields) {
                List<Field> result = new ArrayList<>();
                while (target != null && target != Object.class) {
                    result.addAll(Reflections.getDeclaredFields(target));
                    target = target.getSuperclass();
                }
                return result;
            }
            return Reflections.getDeclaredFields(target);
        }
    }

    /**
     * 设置是否启用字段缓存
     *
     * @param fieldsCacheEnabled 是否启用字段缓存
     */
    public static void setFieldsCacheEnabled(boolean fieldsCacheEnabled) {
        FieldGetters.fieldsCacheEnabled = fieldsCacheEnabled;
    }

}
