package site.lianwu.mybatis.plus.query.reflection;

/**
 * 实例获取接口
 *
 * @param <T> 指定类型
 * @author LianWu
 * @see InstanceGetters
 */
@FunctionalInterface
public interface InstanceGetter<T> {

    /**
     * 获取实例
     *
     * @return 指定类型的实例
     */
    T get();

}
