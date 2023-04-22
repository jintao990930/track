package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperProcessor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperParser extends QueryClassParser {

    default <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass) {
        return parseWrapper(obj, entityClass, false);
    }

    default <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass, boolean includeInheritedFields) {
        return parseWrapper(obj, entityClass, includeInheritedFields, null);
    }

    default <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass, @Nullable QueryWrapperProcessor processor) {
        return parseWrapper(obj, entityClass, false, processor);
    }

    <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass, boolean includeInheritedFields, @Nullable QueryWrapperProcessor processor);

}
