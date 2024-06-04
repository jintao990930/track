package cn.lianwu.mybatis.plus.ext.query.model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleQueryField extends QueryField {

    private final List<PreparedQueryStatement> preparedQueryStatements;

    public SimpleQueryField(Field field, List<PreparedQueryStatement> preparedQueryStatements) {
        super(field, preparedQueryStatements.stream().map(PreparedQueryStatement::getGroupId).collect(Collectors.toSet()));
        this.preparedQueryStatements = preparedQueryStatements;
    }

    public List<PreparedQueryStatement> getPreparedQueryStatements() {
        return preparedQueryStatements;
    }

}
