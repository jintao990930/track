package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryTree {

    private final Class<?> clazz;
    private final boolean includeInheritedFields;
    private final QueryNode root;
    private final Map<String, QueryNode> groupIdMapQueryNode;

    public QueryTree(QueryClass queryClass) {
        clazz = queryClass.getClazz();
        includeInheritedFields = queryClass.isIncludeInheritedFields();
        groupIdMapQueryNode = new HashMap<>(4);
        root = buildTree(queryClass.getQueryGroups(), queryClass.getQueryFields());
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

    private QueryNode buildTree(QueryGroup[] groups, List<QueryField> fields) {
        QueryNode root = new QueryNode(QueryConst.DEFAULT_ROOT_GROUP_ID);
        groupIdMapQueryNode.put(QueryConst.DEFAULT_ROOT_GROUP_ID, root);
        Map<String, String> id2ParentIdMap = new HashMap<>();
        for (QueryGroup g : groups) {
            // skip ROOT_GROUP_ID
            if (Objects.equals(g.id(), QueryConst.DEFAULT_ROOT_GROUP_ID)) {
                continue;
            }
            if (Objects.equals(g.id(), g.parentId())) {
                throw new RuntimeException("The parent of QueryGroupInfo can't be itself.");
            }
            QueryNode node = new QueryNode(g.id(), g.logic());
            // the latter covers the former
            groupIdMapQueryNode.put(g.id(), node);
            id2ParentIdMap.put(g.id(), g.parentId());
        }
        // set parent node
        groupIdMapQueryNode.values().forEach(node -> {
            // skip ROOT QueryNode
            if (Objects.equals(node.getGroupId(), QueryConst.DEFAULT_ROOT_GROUP_ID)) {
                return ;
            }
            String parentGroupId = id2ParentIdMap.get(node.getGroupId());
            QueryNode parentNode = groupIdMapQueryNode.get(parentGroupId);
            if (Objects.isNull(parentNode)) {
                parentNode = new QueryNode(parentGroupId, root);
            }
            node.setParent(parentNode);
            parentNode.addChild(node);
            groupIdMapQueryNode.putIfAbsent(parentGroupId, parentNode);
        });
        checkRingExists(root);
        for (QueryField field : fields) {
            Map<String, Map<Function<Object, Boolean>, List<WhereBlock>>> outerMap = Arrays.stream(field.getQueryColumns())
                    .collect(Collectors.groupingBy(QueryColumn::groupId,
                            Collectors.groupingBy(column -> column.check().getExpression(),
                                    Collectors.mapping(WhereBlock::convert, Collectors.toList()))));
            outerMap.forEach((groupId, innerMap) -> {
                QueryNode node = groupIdMapQueryNode.get(groupId);
                if (Objects.isNull(node)) {
                    node = new QueryNode(groupId, root);
                }
                groupIdMapQueryNode.putIfAbsent(groupId, node);
                Map<Function<Object, Boolean>, List<WhereBlock>> blocksMap = node.getWhereBlocksMap().getOrDefault(field.getField(), new HashMap<>());
                blocksMap.putAll(innerMap);
                node.getWhereBlocksMap().putIfAbsent(field.getField(), blocksMap);
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
            if (!close.add(first.getGroupId())) {
                throw new RuntimeException("There is a ring exists when building QueryTree for Class {" + clazz.getName() + "}." +
                        " GroupId are {" + first.getGroupId()
                        + "} and {" + first.getParent().getGroupId() + "}.");
            }
            open.addAll(first.getChildren());
        }
    }

}
