package site.lianwu.mybatis.plus.query;

import site.lianwu.mybatis.plus.query.model.NestedQueryField;
import site.lianwu.mybatis.plus.query.processor.NestedQueryFieldProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 嵌套查询注解，作用在字段上，表明嵌套查询解析
 *
 * @author LianWu
 * @see NestedQueryField
 * @see NestedQueryFieldProcessor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NestedQuery {

    /**
     * 是否包含继承字段
     * @return true表示包含继承字段，false则表示不包含
     */
    boolean value() default false;

}
