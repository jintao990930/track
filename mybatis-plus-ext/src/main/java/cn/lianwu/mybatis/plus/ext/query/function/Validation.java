package cn.lianwu.mybatis.plus.ext.query.function;

/**
 * 验证接口，用于检查给定值是否满足特定的验证规则
 *
 * @author LianWu
 * @see Validator
 * @see cn.lianwu.mybatis.plus.ext.query.Query
 */
@FunctionalInterface
public interface Validation {

    /**
     * 验证给定值是否有效
     *
     * @param value 待验证的值
     * @return 如果值有效，则返回{@code true}，否则返回{@code false}
     */
    boolean validate(Object value);

}
