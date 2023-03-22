package cn.doocom.mybatis.plus.ext.query.annotation;

import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(QueryGroups.class)
public @interface QueryGroup {

    String id();
    Logic logic() default Logic.AND;

}
