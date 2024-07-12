package site.lianwu.mybatis.plus.query.function;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * 值校验器
 * <br>
 * 以内部静态类的形式定义不同的验证规则以方便使用
 *
 * @author LianWu
 */
public enum Validator {

    ;

    /**
     * 值不为空
     */
    public static class NotEmpty implements Validation {

        @Override
        public boolean validate(Object value) {
            return ObjectUtils.isNotEmpty(value);
        }

    }

    /**
     * 字符串不为空白
     */
    public static class NotBlank implements Validation {
        @Override
        public boolean validate(Object value) {
            if (value instanceof CharSequence) {
                return StringUtils.isNotBlank((CharSequence) value);
            }
            return false;
        }

    }

    /**
     * 无需校验/总是有效
     */
    public static class AlwaysValid implements Validation {

        @Override
        public boolean validate(Object value) {
            return true;
        }

    }

}
