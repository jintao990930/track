package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.Validation;

import java.util.List;

public class QueryField {

    private Class<?> type;
    private List<QueryColumn> queryColumns;
    private Validation validation;

}
