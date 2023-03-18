package cn.doocom.mybatis.ext.annotation;

import cn.doocom.mybatis.ext.enums.Binding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Group
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Group {

    Binding bind() default Binding.AND;

    String id();

}
