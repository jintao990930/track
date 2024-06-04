package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.QueryField;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public interface QueryFieldProcessor {

    Optional<QueryField> extract(Field field);

    List<ExecutableQueryStatement> parse(QueryField queryField, Object fieldValue);

}
