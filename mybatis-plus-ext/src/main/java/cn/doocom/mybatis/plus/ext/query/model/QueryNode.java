package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;

import java.lang.reflect.Field;
import java.util.*;

public class QueryNode {

    private QueryGroupInfo groupInfo;
    private Map<Field, List<QueryColumnInfo>> columnInfoMap;
    private QueryNode parent;
    private List<QueryNode> children;

    QueryNode(QueryGroupInfo groupInfo) {
        this(groupInfo, null);
    }

    QueryNode(QueryGroupInfo groupInfo, QueryNode parent) {
        this.groupInfo = groupInfo;
        columnInfoMap = new HashMap<>();
        this.parent = parent;
        if (Objects.nonNull(parent))
            this.parent.addChild(this);
        children = new ArrayList<>();
    }

    public QueryGroupInfo getGroupInfo() {
        return groupInfo;
    }

    public Map<Field, List<QueryColumnInfo>> getColumnInfoMap() {
        return columnInfoMap;
    }

    void setParent(QueryNode parent) {
        Objects.requireNonNull(parent);
        this.parent = parent;
    }

    public QueryNode getParent() {
        return parent;
    }

    public List<QueryNode> getChildren() {
        return children;
    }

    public void addChild(QueryNode child) {
        Objects.requireNonNull(child);
        children.add(child);
    }

}
