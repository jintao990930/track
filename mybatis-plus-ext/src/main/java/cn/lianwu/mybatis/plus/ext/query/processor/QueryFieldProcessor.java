package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.model.ExecutableQueryStatement;
import cn.lianwu.mybatis.plus.ext.query.model.QueryField;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * 查询字段处理器
 *
 * @author LianWu
 */
public interface QueryFieldProcessor {

    /**
     * 提取{@code Field}上的查询注解并封装成{@link QueryField}，如{@link cn.lianwu.mybatis.plus.ext.query.model.SimpleQueryField}或{@link cn.lianwu.mybatis.plus.ext.query.model.NestedQueryField}等
     *
     * @param field 字段
     * @return 查询字段（{@link QueryField}）
     */
    Optional<QueryField> extract(Field field);

    /**
     * 解析{@link QueryField}，返回{@link ExecutableQueryStatement}列表
     *
     * @param queryField 查询字段
     * @param fieldValue 字段值
     * @return 可执行的查询语句列表，用于构建{code QueryWrapper}
     */
    List<ExecutableQueryStatement> parse(QueryField queryField, Object fieldValue);

}
