package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.model.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;

import java.util.List;

public interface QueryClassProcessor {

    QueryClass extract(Class<?> clz, boolean includeInheritedFields);

    List<ExecutableQueryStatement> parse(QueryClass queryClass, Object obj);

}
