package cn.doocom.mybatis.plus.ext.query.annotation;

import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(QueryGroups.class)
public @interface QueryGroup {

    String id();
    String parentId() default QueryConst.MAIN_GROUP_ID;
    Logic logic() default Logic.AND;

}
