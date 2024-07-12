package site.lianwu.mybatis.plus.query.processor;

import site.lianwu.mybatis.plus.query.QueryConstants;
import site.lianwu.mybatis.plus.query.QueryOption;
import site.lianwu.mybatis.plus.query.function.Logic;
import site.lianwu.mybatis.plus.query.function.PostProcessor;
import site.lianwu.mybatis.plus.query.model.ExecutableQueryStatement;
import site.lianwu.mybatis.plus.query.model.QueryClass;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 默认的查询包装器处理器，用于解析并生成相应的查询条件
 *
 * @author LianWu
 */
public class DefaultQueryWrapperProcessor extends BaseQueryWrapperProcessor {

    /**
     * 默认构造方法，使用默认的查询类处理器{@link DefaultQueryClassProcessor}
     */
    public DefaultQueryWrapperProcessor() {
        super();
    }

    /**
     * 构造方法，允许传入自定义的查询类处理器
     *
     * @param queryClassProcessor 查询类处理器，用于提取和解析查询类
     */
    public DefaultQueryWrapperProcessor(QueryClassProcessor queryClassProcessor) {
        super(queryClassProcessor);
    }


    /**
     * 解析对象，生成查询包装器（{@code QueryWrapper}）
     * <pre>
     * 将可执行的查询语句进行分组，优先处理根分组，其次处理其余分组，分组间约定使用and连接。
     * 以默认的处理器为例，底层使用了无序的数据结构，意味着对其余分组的处理是无序的，同样地，分组内的条件设置也是无序的。
     * <strong>若使用or逻辑条件处理，为确保查询结果的正确性，建议为这些or查询条件声明一个单独的分组。</strong>
     * <strong>后置处理器只针对查询注解中声明的分组有效。即使补充了某个分组的后置处理逻辑，若该分组未在注解中声明，则相应的后置处理逻辑也不会被执行</strong>
     * </pre>
     *
     * @param obj 待解析的对象，可以包含查询条件注解
     * @param includeInheritedFields 是否包含继承字段
     * @param option 查询选项，可配置查询条件后置处理器
     * @param <T> 查询结果的类型
     * @return 返回生成的查询包装器
     */
    @Override
    public <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields, QueryOption<T> option) {
        // 提取查询类信息
        QueryClass queryClass = queryClassProcessor.extract(obj.getClass(), includeInheritedFields);
        // 解析查询类，返回可执行的查询语句
        List<ExecutableQueryStatement> executableQueryStatements = queryClassProcessor.parse(queryClass, obj);
        QueryWrapper<T> wrapper = Wrappers.query();
        // 根据分组ID将查询语句分组
        Map<String, List<ExecutableQueryStatement>> groupId2ExecutableQueryStatements = executableQueryStatements.stream()
                .collect(Collectors.groupingBy(ExecutableQueryStatement::getGroupId));
        // 当前是否存在任何有效的条件。若存在则表明至少存在一个有效分组，用于下文中条件“1 = 1”的补充判断
        AtomicBoolean existsAnyValidCondition = new AtomicBoolean(false);
        // 获取根分组的可执行查询语句
        List<ExecutableQueryStatement> rootStatements = groupId2ExecutableQueryStatements.get(QueryConstants.ROOT_GROUP);
        // 设置根分组中声明的查询条件
        if (CollectionUtils.isNotEmpty(rootStatements)) {
            existsAnyValidCondition.set(true);
            setQueryCondition(wrapper, rootStatements);
        }
        // 补充根分组的查询条件
        getPostProcessor(QueryConstants.ROOT_GROUP, option).ifPresent(rootPostProcessor -> {
            existsAnyValidCondition.set(true);
            rootPostProcessor.accept(wrapper);
        });
        // 处理查询类中声明的其余分组
        queryClass.getGroupIds().forEach(groupId -> {
            if (QueryConstants.ROOT_GROUP.equals(groupId)) {
                // 跳过根分组
                return ;
            }
            List<ExecutableQueryStatement> statements = groupId2ExecutableQueryStatements.get(groupId);
            Optional<PostProcessor<T>> postProcessor = getPostProcessor(groupId, option);
            // 是否为空的有效语句
            boolean emptyValidStatements = CollectionUtils.isEmpty(statements);
            if (emptyValidStatements && !postProcessor.isPresent()) {
                // 该分组下若既不存在有效的条件和也不存在后置处理器，则跳过
                return ;
            }
            if (existsAnyValidCondition.get()) {
                // 当前已存在有效条件，则使用and连接
                wrapper.and(w -> {
                    if (!emptyValidStatements) {
                        // 该分组下存在有效条件，则设置条件值
                        setQueryCondition(w, statements);
                    } else {
                        // 否则表明该分组下只存在后置处理器，则补充“1 = 1”确保QueryWrapper正确被构建
                        w.apply("1 = 1");
                    }
                    // 若存在后置处理器则补充查询条件
                    postProcessor.ifPresent(p -> p.accept(w));
                });
            } else {
                // 当前不存在有效条件，则无需and连接
                if (!emptyValidStatements) {
                    // 该分组下存在有效条件，则设置
                    setQueryCondition(wrapper, statements);
                }
                // 若存在后置处理器则补充查询条件设置
                postProcessor.ifPresent(p -> p.accept(wrapper));
                // 将当前是否存在有效条件布尔值置为true
                existsAnyValidCondition.set(true);
            }
        });
        return wrapper;
    }

    /**
     * 遍历设置查询条件
     *
     * @param wrapper 查询包装器
     * @param executableQueryStatements 查询语句列表
     * @param <T> 查询结果的类型
     */
    private <T> void setQueryCondition(QueryWrapper<T> wrapper, List<ExecutableQueryStatement> executableQueryStatements) {
        executableQueryStatements.forEach(s -> {
            if (Logic.OR == s.getLogic()) {
                wrapper.or();
            }
            s.getCondition().accept(wrapper, s.getColumn(), s.getValue());
        });
    }

}
