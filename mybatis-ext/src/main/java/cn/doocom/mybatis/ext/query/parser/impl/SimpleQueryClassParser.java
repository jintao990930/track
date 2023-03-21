package cn.doocom.mybatis.ext.query.parser.impl;

import cn.doocom.mybatis.ext.query.QueryWrapperBean;
import cn.doocom.mybatis.ext.query.annotation.QueryField;
import cn.doocom.mybatis.ext.query.parser.QueryClassParser;
import cn.doocom.util.AnnotationUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.List;

public class SimpleQueryClassParser implements QueryClassParser {

    private final static String ID_INCLUDE_SUPERCLASS_SUFFIX = "#INCLUDE_SUPERCLASS";

    @Override
    public <T> QueryWrapperBean<T> parseClass(Class<T> clz, boolean includeSuperclass) {
        List<Field> fields = AnnotationUtil.getAnnotatedFields(clz, QueryField.class, includeSuperclass);
        QueryWrapper<T> queryWrapper = Wrappers.query();
        String id = generateId(clz);
        if (includeSuperclass)
            id = id + ID_INCLUDE_SUPERCLASS_SUFFIX;
        return queryWrapperBean(id, queryWrapper);
    }

    private String generateId(Class<?> clz) {
        return clz.getName();
    }

    private <T> QueryWrapperBean<T> queryWrapperBean(String id, QueryWrapper<T> queryWrapper) {
        QueryWrapperBean<T> queryWrapperBean = new QueryWrapperBean<>();
        queryWrapperBean.setId(id);
        queryWrapperBean.setQueryWrapper(queryWrapper);
        return queryWrapperBean;
    }

}
