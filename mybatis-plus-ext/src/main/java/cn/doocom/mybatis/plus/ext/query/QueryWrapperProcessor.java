package cn.doocom.mybatis.plus.ext.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class QueryWrapperProcessor {

    private final Map<String, Consumer<QueryWrapper<?>>> groupPostProcessorMap;

    private QueryWrapperProcessor(Map<String, Consumer<QueryWrapper<?>>> groupPostProcessorMap) {
        this.groupPostProcessorMap = groupPostProcessorMap;
    }

    public QueryWrapperProcessorBuilder builder() {
        return new QueryWrapperProcessorBuilder();
    }

    public Consumer<QueryWrapper<?>> getGroupPostProcessor(String groupId) {
        return groupPostProcessorMap.get(groupId);
    }

    public static class QueryWrapperProcessorBuilder {

        private final Map<String, Consumer<QueryWrapper<?>>> groupPostProcessorMap;

        private QueryWrapperProcessorBuilder() {
            groupPostProcessorMap = new HashMap<>(4);
        }

        public QueryWrapperProcessorBuilder groupPostProcessor(String groupId, Consumer<QueryWrapper<?>> processor) {
            Objects.requireNonNull(processor);
            Consumer<QueryWrapper<?>> groupPostProcessor = groupPostProcessorMap.get(groupId);
            if (Objects.isNull(groupPostProcessor)) {
                groupPostProcessor = processor;
            } else {
                groupPostProcessor = groupPostProcessor.andThen(processor);
            }
            groupPostProcessorMap.put(groupId, groupPostProcessor);
            return this;
        }

        public QueryWrapperProcessor build() {
            return new QueryWrapperProcessor(groupPostProcessorMap);
        }

    }

}
