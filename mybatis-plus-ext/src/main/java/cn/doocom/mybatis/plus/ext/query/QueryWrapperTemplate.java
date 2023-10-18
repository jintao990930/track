package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.parser.QueryWrapperParser;
import cn.doocom.mybatis.plus.ext.query.parser.impl.CacheQueryWrapperParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class QueryWrapperTemplate {

    private final QueryWrapperParser queryWrapperParser;

    public QueryWrapperTemplate() {
        this(new CacheQueryWrapperParser());
    }

    public QueryWrapperTemplate(QueryWrapperParser queryWrapperParser) {
        this.queryWrapperParser = queryWrapperParser;
    }

    public <T> QueryWrapper<T> parse(Object obj) {
        return queryWrapperParser.parseWrapper(obj, false, null);
    }

    public <T> QueryWrapper<T> parse(Object obj, boolean includedSuperclasses) {
        return queryWrapperParser.parseWrapper(obj, includedSuperclasses, null);
    }

    public <T> QueryWrapper<T> parse(Object obj, @Nullable QueryOption<T> option) {
        return queryWrapperParser.parseWrapper(obj, false, option);
    }

    public <T> QueryWrapper<T> parse(Object obj, boolean includedSuperclasses, @Nullable QueryOption<T> option) {
        return queryWrapperParser.parseWrapper(obj, includedSuperclasses, option);
    }

}
