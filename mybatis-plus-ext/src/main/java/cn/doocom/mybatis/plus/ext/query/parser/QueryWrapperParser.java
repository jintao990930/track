package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperProcessor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperParser extends QueryClassParser {

    default <T> QueryWrapper<T> parseWrapper(Object obj) {
        return parseWrapper(obj, false, null);
    }

    default <T> QueryWrapper<T> parseWrapper(Object obj, boolean includeInheritedFields) {
        return parseWrapper(obj, includeInheritedFields, null);
    }

    default <T> QueryWrapper<T> parseWrapper(Object obj, @Nullable QueryWrapperProcessor<T> processor) {
        return parseWrapper(obj, false, processor);
    }

    <T> QueryWrapper<T> parseWrapper(Object obj, boolean includeInheritedFields, @Nullable QueryWrapperProcessor<T> processor);

}
