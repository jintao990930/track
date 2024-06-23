package cn.lianwu.mybatis.plus.query.function;

import cn.lianwu.mybatis.plus.query.QueryOption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Objects;

/**
 * 查询条件后置处理器，用于补充查询条件的构建
 *
 * @param <T> 查询结果类型
 * @author LianWu
 * @see QueryOption
 */
@FunctionalInterface
public interface PostProcessor<T> {

    /**
     * 接受一个查询包装器，补充构建查询条件
     *
     * @param wrapper 查询包装器
     */
    void accept(QueryWrapper<T> wrapper);

    /**
     * 创建并返回一个新的{@code PostProcessor}实例，该实例在调用当前实例的查询条件处理后，接着调用指定的{@code PostProcessor}的处理逻辑
     * <br>
     * 这允许将多个处理逻辑串联起来，按顺序执行
     *
     * @param after 用于在当前处理器之后执行的另一个处理器
     * @return 返回一个新的后置处理器实例，它会依次调用当前实例和指定实例的处理逻辑
     * @throws NullPointerException 若{@code after}参数为null
     */
    default PostProcessor<T> andThen(PostProcessor<T> after) {
        Objects.requireNonNull(after);
        return (wrapper) -> {
            accept(wrapper);
            after.accept(wrapper);
        };
    }

}
