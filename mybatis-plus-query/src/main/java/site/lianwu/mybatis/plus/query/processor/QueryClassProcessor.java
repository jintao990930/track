package site.lianwu.mybatis.plus.query.processor;

import site.lianwu.mybatis.plus.query.model.ExecutableQueryStatement;
import site.lianwu.mybatis.plus.query.model.QueryClass;
import site.lianwu.mybatis.plus.query.model.QueryField;

import java.util.List;

/**
 * 查询类处理器
 *
 * @author LianWu
 */
public interface QueryClassProcessor {

    /**
     * 提取类中的{@link QueryField}并封装成{@link QueryClass}
     *
     * @param clz 目标类
     * @param includeInheritedFields 是否包含继承字段
     * @return 查询类（{@link QueryClass}）
     */
    QueryClass extract(Class<?> clz, boolean includeInheritedFields);

    /**
     * 解析{@link QueryClass}，返回{@link ExecutableQueryStatement}列表
     *
     * @param queryClass 查询类
     * @param obj 对象值
     * @return 可执行的查询语句列表，用于构建{@code QueryWrapper}
     */
    List<ExecutableQueryStatement> parse(QueryClass queryClass, Object obj);

}
