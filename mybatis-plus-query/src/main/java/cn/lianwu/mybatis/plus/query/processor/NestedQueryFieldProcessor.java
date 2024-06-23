package cn.lianwu.mybatis.plus.query.processor;

import cn.lianwu.mybatis.plus.query.NestedQuery;
import cn.lianwu.mybatis.plus.query.model.ExecutableQueryStatement;
import cn.lianwu.mybatis.plus.query.model.NestedQueryField;
import cn.lianwu.mybatis.plus.query.model.QueryClass;
import cn.lianwu.mybatis.plus.query.model.QueryField;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * 嵌套查询字段处理器，处理{@code Field}的{@link NestedQuery}注解
 *
 * @author LianWu
 */
public class NestedQueryFieldProcessor implements QueryFieldProcessor {

    /**
     * 查询类解析器，提取和解析{@code Field}上的{@link NestedQuery}
     */
    protected QueryClassProcessor queryClassProcessor;

    public NestedQueryFieldProcessor(QueryClassProcessor queryClassProcessor) {
        this.queryClassProcessor = queryClassProcessor;
    }

    /**
     * 提取字段上的{@link NestedQuery}注解，并封装成{@link NestedQueryField}
     *
     * @param field 字段
     * @return 嵌套查询字段（{@link NestedQueryField}）
     */
    @Override
    public Optional<QueryField> extract(Field field) {
        NestedQuery nestedQuery = field.getDeclaredAnnotation(NestedQuery.class);
        if (nestedQuery == null) {
            return Optional.empty();
        }
        QueryClass queryClass = queryClassProcessor.extract(field.getType(), nestedQuery.value());
        return Optional.of(new NestedQueryField(field, queryClass));
    }

    /**
     * 解析{@link NestedQueryField}，封装成{@link ExecutableQueryStatement}列表
     *
     * @param queryField 查询字段
     * @param fieldValue 字段值
     * @return 可执行的查询语句片段列表，用于构建{code QueryWrapper}
     */
    @Override
    public List<ExecutableQueryStatement> parse(QueryField queryField, Object fieldValue) {
        if (!(queryField instanceof NestedQueryField) || fieldValue == null) {
            return Collections.emptyList();
        }
        NestedQueryField nestedQueryField = (NestedQueryField) queryField;
        return queryClassProcessor.parse(nestedQueryField.getQueryClass(), fieldValue);
    }

}
