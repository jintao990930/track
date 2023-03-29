package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.Validation;

import java.util.List;

public class QueryField {

    private List<QueryColumn> queryColumns;
    private Validation validation;

    public QueryField() { }

    public List<QueryColumn> getQueryColumns() {
        return queryColumns;
    }

    public void setQueryColumns(List<QueryColumn> queryColumns) {
        this.queryColumns = queryColumns;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

}
