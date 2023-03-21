package cn.doocom.mybatis.ext.query.annotation;

import cn.doocom.mybatis.ext.query.enums.Check;
import cn.doocom.mybatis.ext.query.enums.Operator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryField {

    Operator[] value() default Operator.EQ;
    Check validation() default Check.NONE;
    Group group() default @Group("");

}
