package site.lianwu.mybatis.plus.query.processor;

import site.lianwu.mybatis.plus.query.model.ExecutableQueryStatement;
import site.lianwu.mybatis.plus.query.model.QueryField;
import site.lianwu.mybatis.plus.query.model.NestedQueryField;
import site.lianwu.mybatis.plus.query.model.SimpleQueryField;

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
     * 提取{@code Field}上的查询注解并封装成{@link QueryField}，如{@link SimpleQueryField}或{@link NestedQueryField}等
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
     * @return 可执行的查询语句列表，用于构建{@code QueryWrapper}
     */
    List<ExecutableQueryStatement> parse(QueryField queryField, Object fieldValue);

}
