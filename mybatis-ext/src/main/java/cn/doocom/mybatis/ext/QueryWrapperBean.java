package cn.doocom.mybatis.ext;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

public class QueryWrapperBean<T> {

    private String id;
    private LambdaQueryWrapper<T> queryWrapper;

}
