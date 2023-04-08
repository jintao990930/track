package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryTree {

    private Class<?> clazz;
    private boolean includeInheritedFields;
    private QueryNode root;
    private Map<String, QueryNode> groupIdMapQueryNode;

    public static QueryTreeBuilder builder() {
        return new QueryTreeBuilder();
    }

    private QueryTree(Class<?> clazz, boolean includeInheritedFields,
                      QueryNode root, Map<String, QueryNode> groupIdMapQueryNode) {
        this.clazz = clazz;
        this.includeInheritedFields = includeInheritedFields;
        this.root = root;
        this.groupIdMapQueryNode = groupIdMapQueryNode;
    }

    public static class QueryTreeBuilder {

        private Class<?> clazz;
        private boolean includeInheritedFields;
        private QueryNode root;
        private Map<String, QueryNode> groupIdMapQueryNode;

        private QueryTreeBuilder() { }

        public QueryTreeBuilder queryClass(QueryClass queryClass) {
            clazz = queryClass.getClazz();
            includeInheritedFields = queryClass.isIncludeInheritedFields();
            groupIdMapQueryNode = new HashMap<>(4);
            root = buildTree(queryClass.getQueryGroupInfos(), queryClass.getQueryFields(), groupIdMapQueryNode);
            return this;
        }

        public QueryTree build() {
            return new QueryTree(clazz, includeInheritedFields, root, groupIdMapQueryNode);
        }

        private QueryNode buildTree(List<QueryGroupInfo> groupInfos, List<QueryField> fields, Map<String, QueryNode> nodeMap) {
            return null;
        }

    }

}
