package cn.lianwu.mybatis.plus.ext.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 查询条件接口，定义构建查询条件行为
 *
 * @author LianWu
 * @see ConditionType
 * @see cn.lianwu.mybatis.plus.ext.query.Query
 */
public interface Condition {

    /**
     * 接受一个查询包装器、列名和值，用于构建查询条件
     *
     * @param wrapper 查询包装器，用于封装查询条件
     * @param column 列名，条件中涉及的列
     * @param value 值，指定列的条件值
     */
    void accept(QueryWrapper<?> wrapper, String column, Object value);

}
