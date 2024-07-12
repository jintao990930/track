package site.lianwu.mybatis.plus.query;

import site.lianwu.mybatis.plus.query.processor.DefaultQueryWrapperProcessor;
import site.lianwu.mybatis.plus.query.processor.QueryWrapperProcessor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Objects;

/**
 * 查询包装器工具，{@link QueryWrapperProcessor}的外观类
 * <ul>
 *     <li>
 *         默认使用{@link DefaultQueryWrapperProcessor}进行解析，可通过{@link #setQueryWrapperProcessor(QueryWrapperProcessor)}设置自定义{@code QueryWrapperProcessor}
 *     </li>
 *     <li>
 *         <strong>{@code QueryWrapperProcessor}一经确定则不允许修改</strong>，若实现了自定义{@code QueryWrapperProcessor}，请确保在使用任何解析方法前设置自定义{@code QueryWrapperProcessor}
 *     </li>
 *     <li>
 *         可使用{@link QueryOption}实现对不同分组的查询条件补充
 *     </li>
 * </ul>
 *
 * @author LianWu
 */
public class QueryWrappers {

    /**
     * 查询包装器处理器，负责解析查询条件
     */
    private static volatile QueryWrapperProcessor queryWrapperProcessor;

    private QueryWrappers() { }

    /**
     * 解析查询条件，仅解析类中声明的字段
     *
     * @param obj 查询配置对象，一般为DTO
     * @return 查询条件包装器
     * @param <T> 查询结果类型
     */
    public static <T> QueryWrapper<T> parse(Object obj) {
        return getQueryWrapperProcessor().parse(obj, false, null);
    }

    /**
     * 解析查询条件，不仅包含类中声明的字段，还支持解析从父类继承而来的字段
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
     * 解析查询条件，仅解析类中声明的字段，支持查询选项配置
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
     * 解析查询条件，不仅包含类中声明的字段，还支持解析从父类继承而来的字段、配置查询选项
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
     * <p><strong>{@code QueryWrapperProcessor}一经确定则不允许修改</strong>，若实现了自定义{@code QueryWrapperProcessor}，请确保在使用任何解析方法前设置自定义{@code QueryWrapperProcessor}</p>
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
        if (queryWrapperProcessor == null) {
            synchronized (QueryWrappers.class) {
                if (queryWrapperProcessor == null) {
                    queryWrapperProcessor = new DefaultQueryWrapperProcessor();
                }
            }
        }
        return queryWrapperProcessor;
    }

}
