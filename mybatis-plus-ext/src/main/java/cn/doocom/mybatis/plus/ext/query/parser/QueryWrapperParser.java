package cn.doocom.mybatis.plus.ext.query.parser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperParser extends QueryClassParser, QueryFieldParser {

    default <T> QueryWrapper<T> parse(Class<T> clz) {
        return parse(clz, false);
    }

    <T> QueryWrapper<T> parse(Class<T> clz, boolean includeSuperclass);

}
