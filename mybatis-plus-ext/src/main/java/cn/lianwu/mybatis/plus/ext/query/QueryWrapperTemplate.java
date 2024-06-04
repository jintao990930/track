package cn.lianwu.mybatis.plus.ext.query;

import cn.lianwu.mybatis.plus.ext.query.processor.DefaultQueryWrapperProcessor;
import cn.lianwu.mybatis.plus.ext.query.processor.QueryWrapperProcessor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class QueryWrapperTemplate {

    private final QueryWrapperProcessor queryWrapperProcessor;

    public QueryWrapperTemplate() {
        this(new DefaultQueryWrapperProcessor());
    }

    public QueryWrapperTemplate(QueryWrapperProcessor queryWrapperProcessor) {
        this.queryWrapperProcessor = queryWrapperProcessor;
    }

    public <T> QueryWrapper<T> parse(Object obj) {
        return queryWrapperProcessor.parse(obj, false, null);
    }

    public <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields) {
        return queryWrapperProcessor.parse(obj, includeInheritedFields, null);
    }

    public <T> QueryWrapper<T> parse(Object obj, QueryOption<T> option) {
        return queryWrapperProcessor.parse(obj, false, option);
    }

    public <T> QueryWrapper<T> parse(Object obj, boolean includeInheritedFields, QueryOption<T> option) {
        return queryWrapperProcessor.parse(obj, includeInheritedFields, option);
    }

}
