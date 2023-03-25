package cn.doocom.mybatis.plus.ext.query.annotation;

import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(QueryColumns.class)
public @interface QueryColumn {

    /**
     * alias for operator
     */
    Operator value() default Operator.EQ;
    String column() default QueryConst.HUMP_2_UNDER_LINE_FLAG;
    String groupId() default QueryConst.MAIN_GROUP_ID;
    Logic logic() default Logic.AND;

}
