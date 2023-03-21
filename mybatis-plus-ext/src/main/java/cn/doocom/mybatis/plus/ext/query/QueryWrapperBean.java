package cn.doocom.mybatis.plus.ext.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class QueryWrapperBean<T> {

    private String id;
    private QueryWrapper<T> queryWrapper;

    public QueryWrapperBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryWrapper<T> getQueryWrapper() {
        return queryWrapper;
    }

    public void setQueryWrapper(QueryWrapper<T> queryWrapper) {
        this.queryWrapper = queryWrapper;
    }

}
