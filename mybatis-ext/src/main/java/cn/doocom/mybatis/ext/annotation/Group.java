package cn.doocom.mybatis.ext.annotation;

import cn.doocom.mybatis.ext.enums.Binding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Group {

    String value();
    Binding bind() default Binding.NONE;

}
