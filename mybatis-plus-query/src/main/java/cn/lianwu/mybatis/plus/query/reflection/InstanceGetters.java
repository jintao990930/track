package cn.lianwu.mybatis.plus.query.reflection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实例获取工具，支持实例缓存，可注册实例获取器（{@link InstanceGetter}）
 *
 * @author LianWu
 */
public class InstanceGetters {

    /**
     * 是否启用实例缓存，默认启用
     */
    private static boolean instanceCacheEnabled = true;

    /**
     * 实例缓存表
     */
    private static final Map<Class<?>, Object> instanceCache = new ConcurrentHashMap<>(32);

    /**
     * 实例获取器缓存表
     */
    private static final Map<Class<?>, InstanceGetter<?>> instanceCreatorCache = new ConcurrentHashMap<>(32);

    /**
     * 根据类型获取实例
     * <ul>
     *     <li>若启用实例缓存：
     *         <ul>
     *             <li>先尝试从实例缓存表中获取，若命中缓存直接返回缓存实例</li>
     *             <li>否则，将从实例获取器表中获取，可通过{@link #registerInstanceCreator(Class, InstanceGetter)}注册实例获取器</li>
     *             <li>若无注册实例获取器，则注册默认实例获取器，获取实例后缓存并返回</li>
     *         </ul>
     *     </li>
     *     <li>若禁用实例缓存：
     *         <ul>
     *             <li>直接从实例获取器表中获取，若无注册实例获取器，注册默认实例获取器，获取实例后返回</li>
     *         </ul>
     *     </li>
     * </ul>
     * <p><strong>注册默认实例获取器请参考{@link #getOrDefaultInstanceCreator(Class)}</strong></p>
     *
     * @param clazz 实例所属类
     * @return 实例
     * @param <T> 实例类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        if (instanceCacheEnabled) {
            return (T) instanceCache.computeIfAbsent(clazz, clz -> getOrDefaultInstanceCreator(clz).get());
        }
        return getOrDefaultInstanceCreator(clazz).get();
    }

    /**
     * 获取类的实例获取器，若无注册实例获取器，则注册默认实例获取器并返回
     * <p><strong>使用无参构造器作为默认的实例获取器</strong></p>
     *
     * @param clazz 实例所属类
     * @return 实例获取器
     * @param <T> 实例类型
     */
    @SuppressWarnings("unchecked")
    public static <T> InstanceGetter<T> getOrDefaultInstanceCreator(Class<T> clazz) {
        return (InstanceGetter<T>) instanceCreatorCache.computeIfAbsent(clazz, clz -> () -> Reflections.newInstance(Reflections.getDeclaredConstructor(clz)));
    }

    /**
     * 获取类的实例获取器
     *
     * @param clazz 实例所属类
     * @return 实例获取器
     * @param <T> 实例类型
     */
    @SuppressWarnings("unchecked")
    public static <T> InstanceGetter<T> getInstanceCreator(Class<T> clazz) {
        return (InstanceGetter<T>) instanceCreatorCache.get(clazz);
    }

    /**
     * 注册实例获取器，若已存在，则覆盖
     *
     * @param clazz 实例所属类
     * @param instanceGetter 实例获取器
     * @param <T> 实例类型
     */
    public static <T> void registerInstanceCreator(Class<T> clazz, InstanceGetter<T> instanceGetter) {
        instanceCreatorCache.put(clazz, instanceGetter);
    }

    /**
     * 移除实例获取器
     *
     * @param clazz 实例所属类
     */
    public static void removeInstanceCreator(Class<?> clazz) {
        instanceCreatorCache.remove(clazz);
    }

    /**
     * 设置是否启用实例缓存
     *
     * @param instanceCacheEnabled 是否启用实例缓存
     */
    public static void setInstanceCacheEnabled(boolean instanceCacheEnabled) {
        InstanceGetters.instanceCacheEnabled = instanceCacheEnabled;
    }

}
