package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.QueryConstants;
import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.model.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DefaultQueryWrapperProcessor extends BaseQueryWrapperProcessor {

    public DefaultQueryWrapperProcessor() {
        super();
    }

    public DefaultQueryWrapperProcessor(QueryClassProcessor queryClassProcessor) {
        super(queryClassProcessor);
    }

    @Override
    public <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields, QueryOption<T> option) {
        QueryClass queryClass = queryClassProcessor.extract(obj.getClass(), includeInheritedFields);
        List<ExecutableQueryStatement> executableQueryStatements = queryClassProcessor.parse(queryClass, obj);
        QueryWrapper<T> wrapper = Wrappers.query();
        Map<String, List<ExecutableQueryStatement>> groupId2ExecutableQueryStatements = executableQueryStatements.stream()
                .collect(Collectors.groupingBy(ExecutableQueryStatement::getGroupId));
        setQueryCondition(wrapper, groupId2ExecutableQueryStatements.getOrDefault(QueryConstants.ROOT_GROUP, Collections.emptyList()));
        getPostProcessor(QueryConstants.ROOT_GROUP, option).ifPresent(rootPostProcessor -> rootPostProcessor.accept(wrapper));
        queryClass.getGroupIds().forEach(groupId -> {
            if (QueryConstants.ROOT_GROUP.equals(groupId)) {
                return ;
            }
            List<ExecutableQueryStatement> statements = groupId2ExecutableQueryStatements.get(groupId);
            Optional<Consumer<QueryWrapper<T>>> postProcessor = getPostProcessor(groupId, option);
            boolean emptyStatements = CollectionUtils.isEmpty(statements);
            if (emptyStatements && !postProcessor.isPresent()) {
                return ;
            }
            wrapper.and(w -> {
                if (!emptyStatements) {
                    setQueryCondition(w, statements);
                }
                postProcessor.ifPresent(p -> {
                    if (emptyStatements) {
                        w.apply("1 = 1");
                    }
                    p.accept(w);
                });
            });
        });
        return wrapper;
    }

    private <T> void setQueryCondition(QueryWrapper<T> wrapper, List<ExecutableQueryStatement> executableQueryStatements) {
        executableQueryStatements.forEach(s -> {
            if (Logic.OR == s.getLogic()) {
                wrapper.or();
            }
            s.getOperation().accept(wrapper, s.getColumn(), s.getValue());
        });
    }

}
