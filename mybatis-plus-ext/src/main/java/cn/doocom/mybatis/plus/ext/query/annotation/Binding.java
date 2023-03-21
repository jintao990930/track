package cn.doocom.mybatis.plus.ext.query.annotation;

import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Binding {

    /**
     * alias for id
     */
    String value();
    Logic logic() default Logic.NONE;

}
