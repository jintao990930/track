package cn.doocom.mybatis.plus.ext.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class QueryWrapperProcessor<T> {

    private final Map<String, Consumer<QueryWrapper<T>>> groupPostProcessorMap;

    private QueryWrapperProcessor(Map<String, Consumer<QueryWrapper<T>>> groupPostProcessorMap) {
        this.groupPostProcessorMap = groupPostProcessorMap;
    }

    public static <T> QueryWrapperProcessorBuilder<T> builder() {
        return new QueryWrapperProcessorBuilder<T>();
    }

    public Consumer<QueryWrapper<T>> getGroupPostProcessor(String groupId) {
        return groupPostProcessorMap.get(groupId);
    }

    public static class QueryWrapperProcessorBuilder<T> {

        private final Map<String, Consumer<QueryWrapper<T>>> groupPostProcessorMap;

        private QueryWrapperProcessorBuilder() {
            groupPostProcessorMap = new HashMap<>();
        }

        public QueryWrapperProcessorBuilder<T> groupPostProcessor(String groupId, Consumer<QueryWrapper<T>> processor) {
            Objects.requireNonNull(processor);
            Consumer<QueryWrapper<T>> groupPostProcessor = groupPostProcessorMap.get(groupId);
            if (groupPostProcessor == null) {
                groupPostProcessor = processor;
            } else {
                groupPostProcessor = groupPostProcessor.andThen(processor);
            }
            groupPostProcessorMap.put(groupId, groupPostProcessor);
            return this;
        }

        public QueryWrapperProcessor<T> build() {
            return new QueryWrapperProcessor<T>(groupPostProcessorMap);
        }

    }

}
