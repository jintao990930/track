package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.Query;
import cn.lianwu.mybatis.plus.ext.query.QueryConstants;
import cn.lianwu.mybatis.plus.ext.query.reflection.InstanceGetters;
import cn.lianwu.mybatis.plus.ext.query.model.PreparedQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.QueryField;
import cn.lianwu.mybatis.plus.ext.query.model.SimpleQueryField;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 简单查询字段处理器，处理{@code Field}的{@link Query}注解
 *
 * @author LianWu
 */
public class SimpleQueryFieldProcessor implements QueryFieldProcessor {

    /**
     * 提取字段上的{@link Query}注解，并封装成{@link SimpleQueryField}
     *
     * @param field 字段
     * @return 简单查询字段（{@link SimpleQueryField}）
     */
    @Override
    public Optional<QueryField> extract(Field field) {
        Query[] queries = field.getDeclaredAnnotationsByType(Query.class);
        if (ArrayUtils.isEmpty(queries)) {
            return Optional.empty();
        }
        List<PreparedQueryStatement> preparedQueryStatements = Arrays.stream(queries)
                .map(condition -> {
                    String column = condition.column();
                    if (QueryConstants.HUMP_2_UNDER_LINE_FLAG.equals(column)) {
                        column = StringUtils.camelToUnderline(field.getName());
                    }
                    return new PreparedQueryStatement(InstanceGetters.getInstance(condition.validation()), condition.groupId(),
                            condition.logic(), column, InstanceGetters.getInstance(condition.value()));
                }).collect(Collectors.toList());
        return Optional.of(new SimpleQueryField(field, preparedQueryStatements));
    }

    /**
     * 解析{@link SimpleQueryField}，封装成{@link ExecutableQueryStatement}列表
     *
     * @param queryField 查询字段
     * @param fieldValue 字段值
     * @return 可执行的查询语句片段列表，用于构建{@code QueryWrapper}
     */
    @Override
    public List<ExecutableQueryStatement> parse(QueryField queryField, Object fieldValue) {
        if (!(queryField instanceof SimpleQueryField)) {
            return Collections.emptyList();
        }
        SimpleQueryField simpleQueryField = (SimpleQueryField) queryField;
        List<PreparedQueryStatement> preparedQueryStatements = simpleQueryField.getPreparedQueryStatements();
        List<ExecutableQueryStatement> executableQueryStatements = new ArrayList<>(preparedQueryStatements.size());
        preparedQueryStatements.forEach(preparedStatement -> {
            if (!preparedStatement.getValidation().validate(fieldValue)) {
                return ;
            }
            executableQueryStatements.add(new ExecutableQueryStatement(preparedStatement.getGroupId(), preparedStatement.getLogic(),
                    preparedStatement.getColumn(), preparedStatement.getCondition(), fieldValue));
        });
        return executableQueryStatements;
    }

}
