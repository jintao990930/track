package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.QueryCondition;
import cn.lianwu.mybatis.plus.ext.query.QueryConstants;
import cn.lianwu.mybatis.plus.ext.query.model.PreparedQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.QueryField;
import cn.lianwu.mybatis.plus.ext.query.model.SimpleQueryField;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleQueryFieldProcessor implements QueryFieldProcessor {

    @Override
    public Optional<QueryField> extract(Field field) {
        QueryCondition[] queryConditions = field.getDeclaredAnnotationsByType(QueryCondition.class);
        if (ArrayUtils.isEmpty(queryConditions)) {
            return Optional.empty();
        }
        List<PreparedQueryStatement> preparedQueryStatements = Arrays.stream(queryConditions)
                .map(condition -> {
                    String column = condition.column();
                    if (QueryConstants.HUMP_2_UNDER_LINE_FLAG.equals(column)) {
                        column = StringUtils.camelToUnderline(field.getName());
                    }
                    return new PreparedQueryStatement(condition.validation().getExpression(), condition.groupId(),
                            condition.logic(), column, condition.value().getOperation());
                }).collect(Collectors.toList());
        return Optional.of(new SimpleQueryField(field, preparedQueryStatements));
    }

    @Override
    public List<ExecutableQueryStatement> parse(QueryField queryField, Object fieldValue) {
        if (!(queryField instanceof SimpleQueryField)) {
            return Collections.emptyList();
        }
        SimpleQueryField simpleQueryField = (SimpleQueryField) queryField;
        List<PreparedQueryStatement> preparedQueryStatements = simpleQueryField.getPreparedQueryStatements();
        List<ExecutableQueryStatement> executableQueryStatements = new ArrayList<>(preparedQueryStatements.size());
        preparedQueryStatements.forEach(columnMetadata -> {
            if (!columnMetadata.getValidExpression().apply(fieldValue)) {
                return ;
            }
            executableQueryStatements.add(ExecutableQueryStatement.assemble(columnMetadata, fieldValue));
        });
        return executableQueryStatements;
    }

}
