package cn.doocom.mybatis.ext.query.annotation;

import cn.doocom.mybatis.ext.query.enums.Binding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Group {

    /**
     * alias for id
     * @return
     */
    String value();
    Binding binding() default Binding.NONE;

}
