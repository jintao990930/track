package site.lianwu.mybatis.plus.query.model;

import site.lianwu.mybatis.plus.query.function.Condition;
import site.lianwu.mybatis.plus.query.function.Logic;
import site.lianwu.mybatis.plus.query.function.Validation;
import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.processor.QueryClassProcessor;
import site.lianwu.mybatis.plus.query.processor.QueryFieldProcessor;

import java.lang.reflect.Field;

/**
 * 预备的查询语句，与{@link Query}等价
 * <br>
 * 该类封装了查询语句构建过程中的关键信息，包括数据验证、分组ID、（分组内的）逻辑操作、涉及的列以及查询条件
 *
 * @author LianWu
 * @see QueryClassProcessor#extract(Class, boolean)
 * @see QueryFieldProcessor#extract(Field)
 */
public class PreparedQueryStatement {

    /**
     * 数据验证，验证通过后才视为有效的查询条件
     */
    private final Validation validation;

    /**
     * 分组ID，对查询语句进行分组管理
     */
    private final String groupId;

    /**
     * 逻辑操作符，定义分组内条件间的逻辑关系
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

    public PreparedQueryStatement(Validation validation, String groupId, Logic logic, String column, Condition condition) {
        this.validation = validation;
        this.groupId = groupId;
        this.logic = logic;
        this.column = column;
        this.condition = condition;
    }

    public Validation getValidation() {
        return validation;
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

}
