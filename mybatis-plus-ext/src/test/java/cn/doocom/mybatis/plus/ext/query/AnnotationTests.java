package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.pojo.DemoDTO;
import cn.doocom.util.AnnotationUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.Test;

import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public class AnnotationTests {
    public static void main(String[] args) throws NoSuchFieldException {

        Field keyWord = DemoDTO.class.getDeclaredField("keyWord");
        QueryColumn[] queryColumns = keyWord.getDeclaredAnnotationsByType(QueryColumn.class);
        System.out.println("Arrays.toString(queryColumns) = " + Arrays.toString(queryColumns));
        System.out.println(queryColumns.length);
    }

    @Test
    public void test() {
        DemoDTO str = null;
        Object obj = str;
        System.out.println(StringUtils.isNotBlank((CharSequence) obj));
        System.out.println(obj instanceof String);
    }

}
