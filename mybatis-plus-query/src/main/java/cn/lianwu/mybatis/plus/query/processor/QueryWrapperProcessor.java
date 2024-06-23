package cn.lianwu.mybatis.plus.query.processor;

import cn.lianwu.mybatis.plus.query.QueryOption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 查询包装器处理器
 *
 * @author LianWu
 */
public interface QueryWrapperProcessor {

    /**
     * 解析查询条件
     *
     * @param obj 待解析的查询对象
     * @param includeInheritedFields 是否包含继承字段
     * @param option 查询选项，可补充分组的查询条件
     * @return 查询包装器（{@code QueryWrapper}）
     * @param <T> 查询结果类型
     */
    <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields, QueryOption<T> option);

}
