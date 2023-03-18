package cn.doocom.mybatis.ext.parser.impl;

import cn.doocom.mybatis.ext.QueryWrapperBean;
import cn.doocom.mybatis.ext.annotation.Query;
import cn.doocom.mybatis.ext.parser.QueryWrapperParser;
import cn.doocom.mybatis.ext.util.AnnotationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.List;

public class SimpleQueryWrapperParser implements QueryWrapperParser {

    private final static String ID_INCLUDE_SUPERCLASS_SUFFIX = "#INCLUDE_SUPERCLASS";

    @Override
    public <T> QueryWrapperBean<T> parseClass(Class<T> clz, boolean includeSuperclass) {
        List<Field> fields = AnnotationUtil.getAnnotatedFields(clz, Query.class, includeSuperclass);
        LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery(clz);
        String id = generateId(clz);
        if (includeSuperclass)
            id = id + ID_INCLUDE_SUPERCLASS_SUFFIX;

        return queryWrapperBean(id, queryWrapper);
    }

    private String generateId(Class<?> clz) {
        return clz.getName();
    }

    private <T> QueryWrapperBean<T> queryWrapperBean(String id, LambdaQueryWrapper<T> queryWrapper) {
        QueryWrapperBean<T> queryWrapperBean = new QueryWrapperBean<>();
        queryWrapperBean.setId(id);
        queryWrapperBean.setQueryWrapper(queryWrapper);
        return queryWrapperBean;
    }

}
