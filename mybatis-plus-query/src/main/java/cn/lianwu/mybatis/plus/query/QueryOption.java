package cn.lianwu.mybatis.plus.query;

import cn.lianwu.mybatis.plus.query.function.PostProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 查询选项
 * <pre>
 * 使用{@link #builder()}获取一个建造器{@link QueryOptionBuilder}。
 * 使用{@link QueryOptionBuilder#withPostProcessor(PostProcessor)}实现对根组的查询条件补充。
 * 使用{@link QueryOptionBuilder#withPostProcessor(String, PostProcessor)}实现对指定组的查询条件补充。
 * </pre>
 * <p><strong>QueryOptionBuilder一旦执行构建则不再被允许调用任何构建方法</strong</p>
 *
 * @author LianWu
 * @param <T> 查询结果的类型
 */
public class QueryOption<T> {

    /**
     * 分组ID到后置处理器的映射
     */
    private final Map<String, PostProcessor<T>> groupId2PostProcessorMap;

    private QueryOption(QueryOptionBuilder<T> builder) {
        this.groupId2PostProcessorMap = builder.groupId2PostProcessorMap;
    }

    /**
     * 获取建造器{@link QueryOptionBuilder}
     *
     * @return 建造器
     * @param <T> 查询结果类型
     */
    public static <T> QueryOptionBuilder<T> builder() {
        return new QueryOptionBuilder<>();
    }

    /**
     * 获取指定分组的后置处理器
     *
     * @param groupId 分组ID
     * @return 后置处理器容器对象（可能为空）
     */
    public Optional<PostProcessor<T>> getPostProcessor(String groupId) {
        return groupId2PostProcessorMap == null ? Optional.empty() : Optional.ofNullable(groupId2PostProcessorMap.get(groupId));
    }

    /**
     * {@link QueryOption}建造器
     * <p><strong>一旦调用{@link #build()}后，内部状态则不再允许变更</strong></p>
     *
     * @param <T> 查询结果类型
     */
    public static class QueryOptionBuilder<T> {

        /**
         * 分组ID到后置处理器的映射
         */
        private Map<String, PostProcessor<T>> groupId2PostProcessorMap;

        /**
         * 是否已构建
         */
        private boolean isBuilt;

        private QueryOptionBuilder() {
            // 默认未构建
            isBuilt = false;
        }

        /**
         * 对根组补充后置处理逻辑
         *
         * @param postProcessor 后置处理器
         * @return 自身建造器对象
         */
        public QueryOptionBuilder<T> withPostProcessor(PostProcessor<T> postProcessor) {
            return withPostProcessor(QueryConstants.ROOT_GROUP, postProcessor);
        }

        /**
         * 对指定组补充后置处理逻辑
         *
         * @param groupId 指定组ID
         * @param postProcessor 后置处理器
         * @return 自身建造器对象
         */
        public QueryOptionBuilder<T> withPostProcessor(String groupId, PostProcessor<T> postProcessor) {
            if (isBuilt) {
                throw new IllegalStateException("QueryOptionBuilder已经构建过了");
            }
            if (groupId2PostProcessorMap == null) {
                groupId2PostProcessorMap = new HashMap<>(8);
            }
            PostProcessor<T> groupPostProcessor = groupId2PostProcessorMap.get(groupId);
            if (groupPostProcessor == null) {
                groupPostProcessor = postProcessor;
            } else {
                groupPostProcessor = groupPostProcessor.andThen(postProcessor);
            }
            groupId2PostProcessorMap.put(groupId, groupPostProcessor);
            return this;
        }

        /**
         * 构建{@link QueryOption}
         *
         * @return 查询选项
         */
        public QueryOption<T> build() {
            if (isBuilt) {
                throw new IllegalStateException("QueryOptionBuilder已经构建过了");
            }
            isBuilt = true;
            return new QueryOption<>(this);
        }

    }

}