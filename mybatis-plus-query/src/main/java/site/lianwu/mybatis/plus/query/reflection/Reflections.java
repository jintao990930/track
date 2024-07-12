package site.lianwu.mybatis.plus.query.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 反射工具
 *
 * @author LianWu
 */
public class Reflections {

    /**
     * 获取构造器
     *
     * @param target 目标类
     * @param parameterTypes 参数类型列表
     * @return 构造器
     * @param <T> 实例类型
     */
    public static <T> Constructor<T> getDeclaredConstructor(Class<T> target, Class<?>... parameterTypes) {
        try {
            return target.getDeclaredConstructor(parameterTypes);
        } catch (Exception e) {
            throw new ReflectionException(String.format("获取%s的构造方法失败，参数类型列表：%s", target, Arrays.toString(parameterTypes)), e);
        }
    }

    /**
     * 创建实例
     *
     * @param constructor 构造器
     * @param args 参数列表
     * @return 实例
     * @param <T> 实例类型
     */
    public static <T> T newInstance(Constructor<T> constructor, Object... args) {
        try {
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new ReflectionException(String.format("通过构造方法%s创建实例失败，参数列表：%s", constructor, Arrays.toString(args)), e);
        }
    }

    /**
     * 获取字段值
     *
     * @param field 字段
     * @param target 目标对象
     * @return 字段值
     */
    public static Object getFieldValue(Field field, Object target) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(target);
        } catch (Exception e) {
            throw new ReflectionException(String.format("获取%s的%s字段失败", target.getClass(), field), e);
        }
    }

    /**
     * 获取目标类的声明字段
     *
     * @param target 目标类
     * @return 目标类的声明字段
     */
    public static List<Field> getDeclaredFields(Class<?> target) {
        return Arrays.stream(target.getDeclaredFields()).collect(Collectors.toList());
    }

}
