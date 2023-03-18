package cn.doocom.mybatis.ext;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

public class QueryWrapperBean<T> {

    private String id;
    private LambdaQueryWrapper<T> queryWrapper;

    public QueryWrapperBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LambdaQueryWrapper<T> getQueryWrapper() {
        return queryWrapper;
    }

    public void setQueryWrapper(LambdaQueryWrapper<T> queryWrapper) {
        this.queryWrapper = queryWrapper;
    }

}
