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
        for (QueryGroup g : groups) {
            // dump ROOT QueryGroupInfo
            if (Objects.equals(g.id(), QueryConst.DEFAULT_ROOT_GROUP_ID)) {
                continue;
            }
            if (Objects.equals(g.id(), g.parentId())) {
                throw new RuntimeException("The parent of QueryGroupInfo can't be itself.");
            }
            QueryNode node = new QueryNode(g.id(), g.logic());
            // the latter covers the former
            groupIdMapQueryNode.put(g.id(), node);
        }
        // set parent node
        groupIdMapQueryNode.values().forEach(node -> {
            QueryNode parentNode = groupIdMapQueryNode.get(node.getParent().getGroupId());
            if (Objects.isNull(parentNode)) {
                parentNode = new QueryNode(node.getParent().getGroupId(), root);
            }
            node.setParent(parentNode);
            parentNode.addChild(node);
            groupIdMapQueryNode.putIfAbsent(parentNode.getGroupId(), parentNode);
        });
        checkRingExists(root);
        for (QueryField field : fields) {
            Map<String, Map<Function<Object, Boolean>, List<WhereBlock>>> outerMap = Arrays.stream(field.getQueryColumns())
                    .collect(Collectors.groupingBy(QueryColumn::groupId,
                            Collectors.groupingBy(column -> column.check().getExpression(),
                                    Collectors.mapping(this::convert, Collectors.toList()))));
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

    private WhereBlock convert(QueryColumn queryColumn) {
        return new WhereBlock(queryColumn.value().getOperation(),
                queryColumn.value().getType(),
                queryColumn.column(),
                queryColumn.logic());
    }

}
