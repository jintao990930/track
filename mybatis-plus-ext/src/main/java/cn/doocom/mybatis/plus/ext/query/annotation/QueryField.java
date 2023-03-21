package cn.doocom.mybatis.plus.ext.query.annotation;

import cn.doocom.mybatis.plus.ext.query.enums.Check;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryField {

    /**
     * alias for column
     */
    Column[] value() default {@Column(Operator.EQ)};
    Check check() default Check.NONE;
    Binding binding() default @Binding("");

}
