package site.lianwu.mybatis.plus.query.function;

import site.lianwu.mybatis.plus.query.Query;

/**
 * 逻辑操作符枚举类
 * <br>
 * 定义逻辑运算中常用的两种操作符：{@code and}、{@code or}，表示多个查询条件间的逻辑关系
 *
 * @author LianWu
 * @see Query
 */
public enum Logic {

    /**
     * 逻辑与操作符
     * <br>
     * 表示只有当所有条件都为真时，结果才为真
     */
    AND,


    /**
     * 逻辑或操作符
     * <br>
     * 表示当至少有一个条件为真时，结果就为真
     */
    OR,

}
