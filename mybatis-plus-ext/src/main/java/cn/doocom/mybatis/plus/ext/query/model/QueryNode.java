package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class QueryNode {

    private String groupId;
    private Logic outerLogic;
    private Map<Field, Map<Function<Object, Boolean>, List<WhereBlock>>> whereBlocksMap;
    private QueryNode parent;
    private List<QueryNode> children;

    QueryNode(String groupId) {
        this(groupId, Logic.AND);
    }

    QueryNode(String groupId, Logic outerLogic) {
        this(groupId, outerLogic, null);
    }

    QueryNode(String groupId, QueryNode parent) {
        this(groupId, Logic.AND, parent);
    }

    QueryNode(String groupId, Logic outerLogic, QueryNode parent) {
        this.groupId = groupId;
        this.outerLogic = outerLogic;
        this.whereBlocksMap = new HashMap<>();
        setParent(parent);
        if (Objects.nonNull(parent)) {
            parent.addChild(this);
        }
        children = new ArrayList<>();
    }

    String getGroupId() {
        return groupId;
    }

    public Logic getOuterLogic() {
        return outerLogic;
    }

    public Map<Field, Map<Function<Object, Boolean>, List<WhereBlock>>> getWhereBlocksMap() {
        return whereBlocksMap;
    }

    QueryNode getParent() {
        return parent;
    }

    public List<QueryNode> getChildren() {
        return children;
    }

    public void setParent(QueryNode parent) {
        this.parent = parent;
    }

    public void addChild(QueryNode child) {
        this.children.add(child);
    }

}
