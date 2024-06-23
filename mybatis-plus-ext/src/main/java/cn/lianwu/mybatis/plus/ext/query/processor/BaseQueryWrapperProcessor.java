package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.function.PostProcessor;

import java.util.Optional;

/**
 * 抽象查询包装器处理器类，关联查询类处理器
 *
 * @author LianWu
 */
public abstract class BaseQueryWrapperProcessor implements QueryWrapperProcessor {

    /**
     * 查询类处理器，提取和解析查询类
     */
    protected final QueryClassProcessor queryClassProcessor;

    /**
     * 默认构造方法，使用默认的查询类处理器{@link DefaultQueryClassProcessor}
     */
    public BaseQueryWrapperProcessor() {
        this(new DefaultQueryClassProcessor());
    }

    /**
     * 构造方法，允许传入自定义的查询类处理器
     *
     * @param queryClassProcessor 查询类处理器，用于提取和解析查询类
     */
    public BaseQueryWrapperProcessor(QueryClassProcessor queryClassProcessor) {
        this.queryClassProcessor = queryClassProcessor;
    }

    /**
     * 根据分组ID和查询选项获取后置处理器
     *
     * @param groupId 分组ID
     * @param option 查询选项，可补充分组的查询条件
     * @param <T> 查询结果的类型
     * @return 返回后置处理器的{@code Optional}
     */
    protected <T> Optional<PostProcessor<T>> getPostProcessor(String groupId, QueryOption<T> option) {
        return option == null ? Optional.empty() : option.getPostProcessor(groupId);
    }

}
