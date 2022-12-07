package cn.edu.zhku.util;

import cn.edu.zhku.anno.Component;
import cn.edu.zhku.anno.Controller;
import cn.edu.zhku.anno.RestController;
import cn.edu.zhku.controller.DemoController;
import org.junit.Assert;
import org.junit.Test;

public class AnnotationUtilTest {

    @Test
    public void testIsAnnotationPresent() {
        Assert.assertTrue(AnnotationUtil.isAnnotationPresent(DemoController.class,
                Controller.class));
        Assert.assertTrue(AnnotationUtil.isAnnotationPresent(DemoController.class,
                Component.class));
    }

    @Test
    public void testGetAnnotation() {
        Assert.assertEquals(AnnotationUtil.getAnnotation(DemoController.class,
                Controller.class).getClass().getInterfaces()[0], Controller.class);
        Assert.assertEquals(AnnotationUtil.getAnnotation(DemoController.class,
                Component.class).getClass().getInterfaces()[0], Component.class);
    }

    @Test
    public void testGetAnnotationValue() {
        Assert.assertEquals(AnnotationUtil.getAnnotation(DemoController.class,
                Controller.class).value(), DemoController.class.getAnnotation(RestController.class).value());
        Assert.assertEquals(AnnotationUtil.getAnnotation(DemoController.class,
                Component.class).value(), DemoController.class.getAnnotation(RestController.class).value());
    }

}
