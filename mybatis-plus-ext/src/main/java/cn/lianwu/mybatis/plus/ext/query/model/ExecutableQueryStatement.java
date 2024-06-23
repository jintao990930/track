package cn.lianwu.mybatis.plus.ext.query.model;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.function.Condition;
import cn.lianwu.mybatis.plus.ext.query.function.Logic;

/**
 * 可执行的查询语句，是对通过值验证的{@link PreparedQueryStatement}的进一步抽象
 * <br>
 * 该类封装了查询语句中的关键信息，包括分组ID、（分组内的）逻辑操作、涉及的列、查询条件以及条件值
 *
 * @author LianWu
 * @see cn.lianwu.mybatis.plus.ext.query.processor.QueryWrapperProcessor#parse(Object, boolean, QueryOption)
 * @see cn.lianwu.mybatis.plus.ext.query.processor.QueryClassProcessor#parse(QueryClass, Object)
 * @see cn.lianwu.mybatis.plus.ext.query.processor.QueryFieldProcessor#parse(QueryField, Object)
 */
public class ExecutableQueryStatement {

    /**
     * 分组ID，对查询语句进行分组管理
     */
    private final String groupId;

    /**
     * 逻辑操作符，定义分组内多个条件间的逻辑关系
     */
    private final Logic logic;

    /**
     * 条件中涉及的列名
     */
    private final String column;

    /**
     * 查询条件，定义列值的比较方式
     */
    private final Condition condition;

    /**
     * 条件值
     */
    private final Object value;

    public ExecutableQueryStatement(String groupId, Logic logic, String column, Condition condition, Object value) {
        this.groupId = groupId;
        this.logic = logic;
        this.column = column;
        this.condition = condition;
        this.value = value;
    }

    public String getGroupId() {
        return groupId;
    }

    public Logic getLogic() {
        return logic;
    }

    public String getColumn() {
        return column;
    }

    public Condition getCondition() {
        return condition;
    }

    public Object getValue() {
        return value;
    }

}
