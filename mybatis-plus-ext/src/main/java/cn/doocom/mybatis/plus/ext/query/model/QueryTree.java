package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;

import java.util.*;
import java.util.stream.Collectors;

public class QueryTree {

    private final Class<?> clazz;
    private final boolean includeInheritedFields;
    private final QueryNode root;
    private final Map<String, QueryNode> groupIdMapQueryNode;

    public QueryTree(QueryClass queryClass) {
        Objects.requireNonNull(queryClass);
        clazz = queryClass.getClazz();
        includeInheritedFields = queryClass.isIncludeInheritedFields();
        groupIdMapQueryNode = new HashMap<>(4);
        root = buildTree(queryClass.getQueryGroupInfos(), queryClass.getQueryFields());
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

    private QueryNode buildTree(List<QueryGroupInfo> groupInfos, List<QueryField> fields) {
        QueryGroupInfo rootGroupInfo = new QueryGroupInfo(QueryConst.DEFAULT_ROOT_GROUP_ID);
        QueryNode root = new QueryNode(rootGroupInfo);
        groupIdMapQueryNode.put(rootGroupInfo.getId(), root);
        for (QueryGroupInfo groupInfo : groupInfos) {
            // dump ROOT QueryGroupInfo
            if (Objects.equals(groupInfo.getId(), QueryConst.DEFAULT_ROOT_GROUP_ID)) {
                continue;
            }
            if (Objects.equals(groupInfo.getId(), groupInfo.getParentId())) {
                throw new RuntimeException("The parent of QueryGroupInfo can't be itself.");
            }
            QueryNode node = new QueryNode(groupInfo);
            // the latter covers the former
            groupIdMapQueryNode.put(groupInfo.getId(), node);
        }
        groupIdMapQueryNode.values().forEach(node -> {
            QueryNode parentNode = groupIdMapQueryNode.get(node.getGroupInfo().getParentId());
            if (Objects.isNull(parentNode)) {
                QueryGroupInfo newGroupInfo = new QueryGroupInfo(node.getGroupInfo().getParentId(), QueryConst.DEFAULT_ROOT_GROUP_ID);
                parentNode = new QueryNode(newGroupInfo, root);
            }
            node.setParent(parentNode);
            parentNode.addChild(node);
            groupIdMapQueryNode.putIfAbsent(parentNode.getGroupInfo().getId(), parentNode);
        });
        checkRingExists(root);
        for (QueryField field : fields) {
            Map<String, List<QueryColumnInfo>> map = field.getQueryColumnInfos().stream()
                    .collect(Collectors.groupingBy(QueryColumnInfo::getGroupId,
                            Collectors.toList()));
            map.forEach((id, columnInfos) -> {
                QueryNode node = groupIdMapQueryNode.get(id);
                if (Objects.isNull(node)) {
                    QueryGroupInfo newGroupInfo = new QueryGroupInfo(id, QueryConst.DEFAULT_ROOT_GROUP_ID);
                    node = new QueryNode(newGroupInfo, root);
                }
                groupIdMapQueryNode.putIfAbsent(id, node);
                List<QueryColumnInfo> columnInfoList = node.getColumnInfoMap().getOrDefault(field.getField(), new ArrayList<>());
                columnInfoList.addAll(columnInfos);
                node.getColumnInfoMap().putIfAbsent(field.getField(), columnInfoList);
            });
        }
        return root;
    }

    // bfs check ring exists
    private void checkRingExists(QueryNode root) {
        List<QueryNode> open = new LinkedList<>();
        Set<String> close = new HashSet<>();
        open.add(root);
        while (!open.isEmpty()) {
            QueryNode first = open.remove(0);
            if (!close.add(first.getGroupInfo().getId())) {
                throw new RuntimeException("There is a ring exists when building for QueryClass {" + clazz.getName() + "}." +
                        " QueryGroupInfo#id are {" + first.getGroupInfo().getId()
                        + "} and {" + first.getGroupInfo().getParentId() + "}.");
            }
            open.addAll(first.getChildren());
        }
    }

}
