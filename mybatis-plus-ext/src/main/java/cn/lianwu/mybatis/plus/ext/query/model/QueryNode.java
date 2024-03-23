package cn.lianwu.mybatis.plus.ext.query.model;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class QueryNode {

    private final String groupId;

    private final Map<Field, Map<Function<Object, Boolean>, List<QueryBlock>>> field2ConditionalQueryBlocksMap;

    private List<QueryNode> children;

    QueryNode(String groupId) {
        this(groupId, null);
    }

    QueryNode(String groupId, QueryNode parent) {
        this.groupId = groupId;
        this.field2ConditionalQueryBlocksMap = new HashMap<>(8);
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public Map<Field, Map<Function<Object, Boolean>, List<QueryBlock>>> getField2ConditionalQueryBlocksMap() {
        return field2ConditionalQueryBlocksMap;
    }

    public List<QueryNode> getChildren() {
        return children;
    }

    void addChild(QueryNode child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

}
