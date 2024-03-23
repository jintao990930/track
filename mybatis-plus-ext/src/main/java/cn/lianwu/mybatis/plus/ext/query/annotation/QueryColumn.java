package cn.lianwu.mybatis.plus.ext.query.annotation;

import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.consts.QueryConsts;
import cn.lianwu.mybatis.plus.ext.query.enums.Check;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(QueryColumns.class)
public @interface QueryColumn {

    /**
     * alias for operator
     */
    Operator value() default Operator.EQ;

    String column() default QueryConsts.HUMP_2_UNDER_LINE_FLAG;

    String groupId() default QueryConsts.DEFAULT_ROOT_GROUP_ID;

    Logic logic() default Logic.AND;

    Check check() default Check.NOT_EMPTY;

}
