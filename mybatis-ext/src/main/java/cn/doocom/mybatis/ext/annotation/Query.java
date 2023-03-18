package cn.doocom.mybatis.ext.annotation;

import cn.doocom.mybatis.ext.enums.Binding;
import cn.doocom.mybatis.ext.enums.Check;
import cn.doocom.mybatis.ext.enums.Operator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Query {

    Operator value() default Operator.EQ;
    Check validation() default Check.NONE;
    Group group() default @Group(bind = Binding.NONE, id = "");
    String column() default "";

}
