package cn.lianwu.mybatis.plus.query;

import cn.lianwu.mybatis.plus.query.function.*;
import cn.lianwu.mybatis.plus.query.model.QueryField;
import cn.lianwu.mybatis.plus.query.processor.SimpleQueryFieldProcessor;

import java.lang.annotation.*;

/**
 * 查询注解，（可重复）作用在字段上，用于声明查询条件的构建
 *
 * @author LianWu
 * @see QueryField
 * @see SimpleQueryFieldProcessor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(QueryContainer.class)
public @interface Query {

    /**
     * 校验类，默认字段值不为空
     */
    Class<? extends Validation> validation() default Validator.NotEmpty.class;

    /**
     * 分组ID（分组是对SQL中括号的抽象），默认根组，即查询条件最外层括号{@code WHERE (...)}
     */
    String groupId() default QueryConstants.ROOT_GROUP;

    /**
     * 逻辑操作符，默认（and），表示分组内条件间的逻辑关系
     */
    Logic logic() default Logic.AND;

    /**
     * 列名，默认驼峰转下划线
     */
    String column() default QueryConstants.HUMP_2_UNDER_LINE_FLAG;

    /**
     * 运算类，默认Eq（=）
     */
    Class<? extends Condition> value() default ConditionType.Eq.class;

}
