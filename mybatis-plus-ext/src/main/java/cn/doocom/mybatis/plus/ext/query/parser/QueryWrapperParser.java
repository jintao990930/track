package cn.doocom.mybatis.plus.ext.query.parser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperParser extends QueryClassParser {

    default <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass) {
        return parse(obj, entityClass, false);
    }

    <T> QueryWrapper<T> parse(Object obj, Class<T> entityClass, boolean includeSuperclass);

}
