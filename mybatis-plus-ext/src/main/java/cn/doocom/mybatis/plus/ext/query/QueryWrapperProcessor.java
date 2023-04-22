package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class QueryWrapperProcessor<T> {

    private final Map<String, Consumer<QueryWrapper<T>>> postProcessorMap;

    private QueryWrapperProcessor(Map<String, Consumer<QueryWrapper<T>>> postProcessorMap) {
        this.postProcessorMap = postProcessorMap;
    }

    public static <T> QueryWrapperProcessorBuilder<T> builder() {
        return new QueryWrapperProcessorBuilder<T>();
    }

    public Consumer<QueryWrapper<T>> getPostProcessor(String groupId) {
        return postProcessorMap.get(groupId);
    }

    public static class QueryWrapperProcessorBuilder<T> {

        private final Map<String, Consumer<QueryWrapper<T>>> postProcessorMap;

        private QueryWrapperProcessorBuilder() {
            postProcessorMap = new HashMap<>();
        }

        public QueryWrapperProcessorBuilder<T> with(Consumer<QueryWrapper<T>> postProcessor) {
            return with(QueryConst.DEFAULT_ROOT_GROUP_ID, postProcessor);
        }

        public QueryWrapperProcessorBuilder<T> with(String groupId, Consumer<QueryWrapper<T>> postProcessor) {
            Objects.requireNonNull(postProcessor);
            Consumer<QueryWrapper<T>> groupPostProcessor = postProcessorMap.get(groupId);
            if (groupPostProcessor == null) {
                groupPostProcessor = postProcessor;
            } else {
                groupPostProcessor = groupPostProcessor.andThen(postProcessor);
            }
            postProcessorMap.put(groupId, groupPostProcessor);
            return this;
        }

        public QueryWrapperProcessor<T> build() {
            return new QueryWrapperProcessor<T>(postProcessorMap);
        }

    }

}
