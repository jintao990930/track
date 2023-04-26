package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class QueryOption<T> {

    @Nullable
    private final Map<String, Consumer<QueryWrapper<T>>> postProcessorMap;

    private QueryOption(Map<String, Consumer<QueryWrapper<T>>> postProcessorMap) {
        this.postProcessorMap = postProcessorMap;
    }

    public static <T> QueryOptionBuilder<T> builder() {
        return new QueryOptionBuilder<T>();
    }

    @Nullable
    public Consumer<QueryWrapper<T>> getPostProcessor(String groupId) {
        return postProcessorMap == null ? null : postProcessorMap.get(groupId);
    }

    public static class QueryOptionBuilder<T> {

        @Nullable
        private Map<String, Consumer<QueryWrapper<T>>> postProcessorMap;

        private QueryOptionBuilder() { }

        public QueryOptionBuilder<T> withProcessor(Consumer<QueryWrapper<T>> postProcessor) {
            return withProcessor(QueryConst.DEFAULT_ROOT_GROUP_ID, postProcessor);
        }

        public QueryOptionBuilder<T> withProcessor(String groupId, Consumer<QueryWrapper<T>> postProcessor) {
            Objects.requireNonNull(postProcessor);
            if (Objects.isNull(postProcessorMap)) {
                postProcessorMap = new HashMap<>();
            }
            Consumer<QueryWrapper<T>> groupPostProcessor = postProcessorMap.get(groupId);
            if (groupPostProcessor == null) {
                groupPostProcessor = postProcessor;
            } else {
                groupPostProcessor = groupPostProcessor.andThen(postProcessor);
            }
            postProcessorMap.put(groupId, groupPostProcessor);
            return this;
        }

        public QueryOption<T> build() {
            return new QueryOption<T>(postProcessorMap);
        }

    }

}