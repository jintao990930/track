package cn.doocom.mybatis.plus.ext.query.annotation;

import cn.doocom.mybatis.plus.ext.query.enums.Operator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Column {

    /**
     * alias for operator
     */
    Operator value();
    String column() default "";

}
