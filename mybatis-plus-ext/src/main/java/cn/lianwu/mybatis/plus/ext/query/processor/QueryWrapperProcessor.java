package cn.lianwu.mybatis.plus.ext.query.processor;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperProcessor {

    <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields, QueryOption<T> option);

}
