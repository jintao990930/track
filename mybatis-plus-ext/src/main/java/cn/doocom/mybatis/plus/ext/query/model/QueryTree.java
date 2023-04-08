package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryTree {

    private final Class<?> clazz;
    private final boolean includeInheritedFields;
    private final QueryNode root;
    private final Map<String, QueryNode> groupIdMapQueryNode;

    public QueryTree(QueryClass queryClass) {
        clazz = queryClass.getClazz();
        includeInheritedFields = queryClass.isIncludeInheritedFields();
        groupIdMapQueryNode = new HashMap<>(4);
        root = buildTree(queryClass.getQueryGroupInfos(), queryClass.getQueryFields(), groupIdMapQueryNode);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public boolean isIncludeInheritedFields() {
        return includeInheritedFields;
    }

    public QueryNode getRoot() {
        return root;
    }

    public Map<String, QueryNode> getGroupIdMapQueryNode() {
        return groupIdMapQueryNode;
    }

    private QueryNode buildTree(List<QueryGroupInfo> groupInfos, List<QueryField> fields, Map<String, QueryNode> nodeMap) {

        return root;
    }

}
