package cn.doocom.mybatis.plus.ext.query.parser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperParser extends QueryClassParser {

    default <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass) {
        return parseWrapper(obj, entityClass, false);
    }

    <T> QueryWrapper<T> parseWrapper(Object obj, Class<T> entityClass, boolean includeSuperclass);

}
