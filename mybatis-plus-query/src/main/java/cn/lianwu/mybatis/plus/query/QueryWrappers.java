package cn.lianwu.mybatis.plus.query;

import cn.lianwu.mybatis.plus.query.processor.DefaultQueryWrapperProcessor;
import cn.lianwu.mybatis.plus.query.processor.QueryWrapperProcessor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Objects;

/**
 * 查询包装器工具，{@code QueryWrapperProcessor}的外观类
 * 默认使用{@link DefaultQueryWrapperProcessor}进行解析，可通过{@link QueryWrappers#setQueryWrapperProcessor(QueryWrapperProcessor)}设置自定义{@code QueryWrapperProcessor}
 * {@code QueryWrapperProcessor}一经设置则不允许修改
 * 可使用{@link QueryOption}实现对不同组的查询条件补充
 *
 * @author LianWu
 */
public class QueryWrappers {

    /**
     * 查询包装器处理器，负责解析查询条件
     */
    private static QueryWrapperProcessor queryWrapperProcessor;

    private QueryWrappers() {
        // 防止实例化
    }

    /**
     * 解析查询条件，仅解析类中声明的所有字段
     *
     * @param obj 查询配置对象，一般为DTO
     * @return 查询条件包装器
     * @param <T> 查询结果类型
     */
    public static <T> QueryWrapper<T> parse(Object obj) {
        return getQueryWrapperProcessor().parse(obj, false, null);
    }

    /**
     * 解析查询条件，不仅包含类中声明的所有字段，还支持解析从父类继承而来的字段
     *
     * @param obj 查询配置对象，一般为DTO
     * @param includeInheritedFields 是否包含从父类继承而来的字段
     * @return 查询条件包装器
     * @param <T> 查询结果类型
     */
    public static <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields) {
        return getQueryWrapperProcessor().parse(obj, includeInheritedFields, null);
    }

    /**
     * 解析查询条件，仅解析类中声明的所有字段，支持查询选项配置
     *
     * @param obj 查询配置对象，一般为DTO
     * @param option 查询选项，可对分组补充后置处理逻辑
     * @return 查询条件包装器
     * @param <T> 查询结果类型
     */
    public static <T> QueryWrapper<T> parse(Object obj, QueryOption<T> option) {
        return getQueryWrapperProcessor().parse(obj, false, option);
    }

    /**
     * 解析查询条件，不仅包含类中声明的所有字段，还支持解析从父类继承而来的字段、配置查询选项
     *
     * @param obj 查询配置对象，一般为DTO
     * @param includeInheritedFields 是否包含从父类继承而来的字段
     * @param option 查询选项，可对分组补充后置处理逻辑
     * @return 查询条件包装器
     * @param <T> 查询结果类型
     */
    public static <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields, QueryOption<T> option) {
        return getQueryWrapperProcessor().parse(obj, includeInheritedFields, option);
    }

    /**
     * 设置查询包装器处理器
     * <p><strong>一经设置则不再允许修改</strong></p>
     *
     * @param queryWrapperProcessor 查询包装器处理器
     */
    public static void setQueryWrapperProcessor(QueryWrapperProcessor queryWrapperProcessor) {
        Objects.requireNonNull(queryWrapperProcessor);
        synchronized (QueryWrappers.class) {
            if (QueryWrappers.queryWrapperProcessor != null) {
                throw new IllegalStateException("QueryWrapperProcessor已经被设置了");
            }
            QueryWrappers.queryWrapperProcessor = queryWrapperProcessor;
        }
    }

    /**
     * 获取查询包装器处理器实例
     * <pre>
     * 可调用{@link QueryWrappers#setQueryWrapperProcessor(QueryWrapperProcessor)}设置自定义处理器。
     * 默认使用双检锁模式实现懒汉式初始化{@link DefaultQueryWrapperProcessor}进行解析，确保线程安全和处理器实例的唯一性。
     * </pre>
     *
     * @return 查询包装器处理器实例
     */
    private static QueryWrapperProcessor getQueryWrapperProcessor() {
        QueryWrapperProcessor localProcessor = queryWrapperProcessor;
        if (localProcessor == null) {
            synchronized (QueryWrappers.class) {
                localProcessor = queryWrapperProcessor;
                if (localProcessor == null) {
                    queryWrapperProcessor = localProcessor = DefaultQueryWrapperProcessorHolder.INSTANCE;
                }
            }
        }
        return localProcessor;
    }

    /**
     * 默认查询包装器处理器的持有类
     * <br>
     * 使用静态内部类的方式实现单例
     */
    private static class DefaultQueryWrapperProcessorHolder {

        private static final QueryWrapperProcessor INSTANCE = new DefaultQueryWrapperProcessor();

    }

}
