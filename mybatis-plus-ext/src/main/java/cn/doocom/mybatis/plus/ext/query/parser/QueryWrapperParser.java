package cn.doocom.mybatis.plus.ext.query.parser;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.QueryOption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface QueryWrapperParser extends QueryClassParser {

    default <T> QueryWrapper<T> parseWrapper(Object obj) {
        return parseWrapper(obj, false, null);
    }

    default <T> QueryWrapper<T> parseWrapper(Object obj, boolean includedSuperclasses) {
        return parseWrapper(obj, includedSuperclasses, null);
    }

    default <T> QueryWrapper<T> parseWrapper(Object obj, @Nullable QueryOption<T> option) {
        return parseWrapper(obj, false, option);
    }

    <T> QueryWrapper<T> parseWrapper(Object obj, boolean includedSuperclasses, @Nullable QueryOption<T> option);

}
