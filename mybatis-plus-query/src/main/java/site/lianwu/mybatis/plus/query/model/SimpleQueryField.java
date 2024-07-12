package site.lianwu.mybatis.plus.query.model;

import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.processor.SimpleQueryFieldProcessor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 简单查询字段，对{@link Query}作用的{@code Field}的封装
 *
 * @author LianWu
 * @see Query
 * @see SimpleQueryFieldProcessor
 */
public class SimpleQueryField extends QueryField {

    /**
     * 预备的查询语句列表，是提取{@link Query}的结果封装
     */
    private final List<PreparedQueryStatement> preparedQueryStatements;

    public SimpleQueryField(Field field, List<PreparedQueryStatement> preparedQueryStatements) {
        super(field, preparedQueryStatements.stream().map(PreparedQueryStatement::getGroupId).collect(Collectors.toSet()));
        this.preparedQueryStatements = preparedQueryStatements;
    }

    public List<PreparedQueryStatement> getPreparedQueryStatements() {
        return preparedQueryStatements;
    }

}
