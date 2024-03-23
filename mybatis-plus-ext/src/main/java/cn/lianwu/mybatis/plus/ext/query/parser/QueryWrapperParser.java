package cn.lianwu.mybatis.plus.ext.query.parser;

import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperParser extends QueryClassParser {

    <T> QueryWrapper<T> parseWrapper(Object obj, boolean includedSuperclasses, QueryOption<T> option);

}
