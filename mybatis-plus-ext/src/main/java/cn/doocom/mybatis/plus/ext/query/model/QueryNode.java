package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.common.annotation.Nullable;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class QueryNode {

    private final String groupId;
    private final Logic outerLogic;
    private final Map<Field, Map<Function<Object, Boolean>, List<QueryBlock>>> queryBlocksMap;
    /**
     * only when ROOT QueryNode, parent is null
     */
    @Nullable
    private QueryNode parent;
    @Nullable
    private List<QueryNode> children;

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
        this.queryBlocksMap = new HashMap<>();
        setParent(parent);
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public Logic getOuterLogic() {
        return outerLogic;
    }

    public Map<Field, Map<Function<Object, Boolean>, List<QueryBlock>>> getQueryBlocksMap() {
        return queryBlocksMap;
    }

    public QueryNode getParent() {
        return parent;
    }

    public List<QueryNode> getChildren() {
        return children;
    }

    void setParent(QueryNode parent) {
        this.parent = parent;
    }

    void addChild(QueryNode child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

}