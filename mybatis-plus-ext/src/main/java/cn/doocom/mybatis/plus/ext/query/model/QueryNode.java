package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class QueryNode {

    private final String groupId;
    private final Logic outerLogic;
    private final Map<Field, Map<Function<Object, Boolean>, List<WhereBlock>>> whereBlocksMap;
    private QueryNode parent;
    private final List<QueryNode> children;

    public QueryNode(String groupId) {
        this(groupId, Logic.AND);
    }

    public QueryNode(String groupId, Logic outerLogic) {
        this(groupId, outerLogic, null);
    }

    public QueryNode(String groupId, QueryNode parent) {
        this(groupId, Logic.AND, parent);
    }

    public QueryNode(String groupId, Logic outerLogic, QueryNode parent) {
        this.groupId = groupId;
        this.outerLogic = outerLogic;
        this.whereBlocksMap = new HashMap<>();
        setParent(parent);
        if (Objects.nonNull(parent)) {
            parent.addChild(this);
        }
        children = new ArrayList<>();
    }

    public String getGroupId() {
        return groupId;
    }

    public Logic getOuterLogic() {
        return outerLogic;
    }

    public Map<Field, Map<Function<Object, Boolean>, List<WhereBlock>>> getWhereBlocksMap() {
        return whereBlocksMap;
    }

    public QueryNode getParent() {
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
