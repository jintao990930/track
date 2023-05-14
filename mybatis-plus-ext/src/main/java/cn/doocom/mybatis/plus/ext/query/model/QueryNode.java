package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.common.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class QueryNode {

    private final String groupId;
    private final Map<Field, Map<Function<Object, Boolean>, List<QueryBlock>>> queryBlocksMap;
    @Nullable
    private List<QueryNode> children;

    QueryNode(String groupId) {
        this(groupId, null);
    }

    QueryNode(String groupId, @Nullable QueryNode parent) {
        this.groupId = groupId;
        this.queryBlocksMap = new HashMap<>(8);
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public Map<Field, Map<Function<Object, Boolean>, List<QueryBlock>>> getQueryBlocksMap() {
        return queryBlocksMap;
    }

    @Nullable
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
