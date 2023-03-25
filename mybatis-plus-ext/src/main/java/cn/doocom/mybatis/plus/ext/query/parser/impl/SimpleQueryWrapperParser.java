package cn.doocom.mybatis.plus.ext.query.parser.impl;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.parser.QueryWrapperParser;
import cn.doocom.util.AnnotationUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.lang.reflect.Field;
import java.util.List;

public class SimpleQueryWrapperParser implements QueryWrapperParser {

    @Override
    public <T> QueryWrapper<T> parse(Class<T> clz, boolean includeSuperclass) {
        List<Field> fields = AnnotationUtil.getAnnotatedFields(clz, QueryColumn.class, includeSuperclass);
        QueryWrapper<T> queryWrapper = Wrappers.query();
        return queryWrapper;
    }

}
