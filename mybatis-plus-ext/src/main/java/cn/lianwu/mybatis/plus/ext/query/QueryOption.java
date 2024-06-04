package cn.lianwu.mybatis.plus.ext.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class QueryOption<T> {

    private final Map<String, Consumer<QueryWrapper<T>>> groupId2PostProcessorMap;

    private QueryOption(QueryOptionBuilder<T> builder) {
        this.groupId2PostProcessorMap = builder.groupId2PostProcessorMap;
    }

    public static <T> QueryOptionBuilder<T> builder() {
        return new QueryOptionBuilder<>();
    }

    public Optional<Consumer<QueryWrapper<T>>> getPostProcessor(String groupId) {
        return groupId2PostProcessorMap == null ? Optional.empty() : Optional.ofNullable(groupId2PostProcessorMap.get(groupId));
    }

    public static class QueryOptionBuilder<T> {

        private Map<String, Consumer<QueryWrapper<T>>> groupId2PostProcessorMap;

        private QueryOptionBuilder() { }

        public QueryOptionBuilder<T> withPostProcessor(Consumer<QueryWrapper<T>> postProcessor) {
            return withPostProcessor(QueryConstants.ROOT_GROUP, postProcessor);
        }

        public QueryOptionBuilder<T> withPostProcessor(String groupId, Consumer<QueryWrapper<T>> postProcessor) {
            Objects.requireNonNull(postProcessor);
            if (groupId2PostProcessorMap == null) {
                groupId2PostProcessorMap = new HashMap<>(8);
            }
            Consumer<QueryWrapper<T>> groupPostProcessor = groupId2PostProcessorMap.get(groupId);
            if (groupPostProcessor == null) {
                groupPostProcessor = postProcessor;
            } else {
                groupPostProcessor = groupPostProcessor.andThen(postProcessor);
            }
            groupId2PostProcessorMap.put(groupId, groupPostProcessor);
            return this;
        }

        public QueryOption<T> build() {
            return new QueryOption<>(this);
        }

    }

}