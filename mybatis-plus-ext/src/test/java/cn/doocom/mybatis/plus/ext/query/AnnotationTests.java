package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.pojo.DemoDTO;
import cn.doocom.util.AnnotationUtil;

import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.util.List;


public class AnnotationTests {
    public static void main(String[] args) {
        List<Field> annotatedFields =
                AnnotationUtil.getAnnotatedFields(DemoDTO.class, QueryColumn.class);
        System.out.println("annotatedFields = " + annotatedFields);
    }
}
