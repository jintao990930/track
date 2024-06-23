package cn.lianwu.mybatis.plus.ext.query.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Collection;

/**
 * 查询条件类型枚举类
 * <br>
 * 以静态内部类的形式定义不同的查询条件以方便使用
 *
 * @author LianWu
 */
public enum ConditionType {
    
    ;

    /**
     * Eq类定义等于操作，如：{@code column = value}
     */
    public static class Eq implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.eq(column, value);
        }
    }

    /**
     * Ne类定义不等于操作，如：{@code column <> value}
     */
    public static class Ne implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.ne(column, value);
        }
    }

    /**
     * Gt类定义大于操作，如：{@code column > value}
     */
    public static class Gt implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.gt(column, value);
        }
    }

    /**
     * Ge类定义大于或等于操作，如：{@code column >= value}
     */
    public static class Ge implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.ge(column, value);
        }
    }

    /**
     * Lt类定义小于操作，如：{@code column < value}
     */
    public static class Lt implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.lt(column, value);
        }
    }

    /**
     * Le类定义小于或等于操作，如：{@code column <= value}
     */
    public static class Le implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.le(column, value);
        }
    }

    /**
     * Like类定义字符串模糊匹配操作，如：{@code column LIKE '%value%'}
     */
    public static class Like implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.like(column, value);
        }
    }

    /**
     * NotLike类定义字符串非模糊匹配操作，如：{@code column NOT LIKE '%value%'}
     */
    public static class NotLike implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.notLike(column, value);
        }
    }

    /**
     * LikeLeft类定义字符串后缀匹配操作，如：{@code column LIKE '%value'}
     */
    public static class LikeLeft implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.likeLeft(column, value);
        }
    }

    /**
     * LikeRight类定义字符串前缀匹配操作，如：{@code column LIKE 'value%'}
     */
    public static class LikeRight implements Condition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Object value) {
            wrapper.likeRight(column, value);
        }
    }

    /**
     * In类定义多值匹配操作，如：{@code column IN values}
     */
    public static class In implements MultiValueCondition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Collection<Object> values) {
            wrapper.in(column, values);
        }
    }

    /**
     * NotIn类定义多值排除操作，如：{@code column NOT IN values}
     */
    public static class NotIn implements MultiValueCondition {
        @Override
        public void accept(QueryWrapper<?> wrapper, String column, Collection<Object> values) {
            wrapper.notIn(column, values);
        }
    }

}
