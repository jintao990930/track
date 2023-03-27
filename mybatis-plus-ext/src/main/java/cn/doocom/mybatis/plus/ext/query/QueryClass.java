package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;

import java.util.List;

public class QueryClass {

    private Class<?> type;
    private boolean withSuperclass;
    private List<QueryField> queryFields;
    private List<QueryGroup> queryGroups;

}
