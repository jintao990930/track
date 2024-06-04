package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Optional;
import java.util.function.Consumer;

public abstract class BaseQueryWrapperProcessor implements QueryWrapperProcessor {

    protected final QueryClassProcessor queryClassProcessor;

    public BaseQueryWrapperProcessor() {
        this(new DefaultQueryClassProcessor());
    }

    public BaseQueryWrapperProcessor(QueryClassProcessor queryClassProcessor) {
        this.queryClassProcessor = queryClassProcessor;
    }

    protected <T> Optional<Consumer<QueryWrapper<T>>> getPostProcessor(String groupId, QueryOption<T> option) {
        return option == null ? Optional.empty() : option.getPostProcessor(groupId);
    }

}
