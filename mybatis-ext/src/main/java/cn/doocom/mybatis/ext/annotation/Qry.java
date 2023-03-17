package cn.doocom.mybatis.ext.annotation;

import cn.doocom.mybatis.ext.enums.Bn;
import cn.doocom.mybatis.ext.enums.Ck;
import cn.doocom.mybatis.ext.enums.Op;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Query
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Qry {

    Op value() default Op.EQ;
    Ck validation() default Ck.NONE;

    Gp group() default @Gp(bind = Bn.NONE, id = "-1");

}
