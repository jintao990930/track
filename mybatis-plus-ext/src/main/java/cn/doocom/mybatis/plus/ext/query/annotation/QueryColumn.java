package cn.doocom.mybatis.plus.ext.query.annotation;

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
    String column() default "";
    String groupId() default "";
    Logic logic() default Logic.AND;

}
