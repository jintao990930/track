package cn.lianwu.mybatis.plus.ext.query;

import cn.lianwu.mybatis.plus.ext.query.enums.Validation;
import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(QueryConditionContainer.class)
public @interface QueryCondition {

    Validation validation() default Validation.NOT_EMPTY;

    String groupId() default QueryConstants.ROOT_GROUP;

    Logic logic() default Logic.AND;

    String column() default QueryConstants.HUMP_2_UNDER_LINE_FLAG;

    /**
     * alias for operator
     */
    Operator value() default Operator.EQ;

}
