package cn.doocom.mybatis.ext.annotation;

import cn.doocom.mybatis.ext.enums.Bn;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Group
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Gp {

    Bn bind() default Bn.AND;

    String id();

}
