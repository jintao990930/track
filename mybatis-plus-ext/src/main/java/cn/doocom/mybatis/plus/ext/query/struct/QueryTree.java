package cn.doocom.mybatis.plus.ext.query.struct;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.exception.StructDefinitionException;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryTree {

    private final Class<?> sourceClass;
    private final boolean includeInheritedFields;
    private final QueryNode root;
    private final Map<String, QueryNode> queryNodeMap;

    public QueryTree(QueryClass queryClass) {
        sourceClass = queryClass.getSourceClass();
        includeInheritedFields = queryClass.isIncludeInheritedFields();
        queryNodeMap = new HashMap<>();
        root = buildTree(queryClass.getQueryGroups(), queryClass.getQueryFields());
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public boolean isIncludeInheritedFields() {
        return includeInheritedFields;
    }

    public QueryNode getRoot() {
        return root;
    }

    public Map<String, QueryNode> getQueryNodeMap() {
        return queryNodeMap;
    }

    private QueryNode buildTree(QueryGroup[] groups, List<QueryField> fields) {
        QueryNode root = new QueryNode(QueryConst.DEFAULT_ROOT_GROUP_ID);
        queryNodeMap.put(QueryConst.DEFAULT_ROOT_GROUP_ID, root);
        Map<String, String> id2ParentIdMap = new HashMap<>();
        for (QueryGroup g : groups) {
            // skip ROOT_GROUP_ID
            if (QueryConst.DEFAULT_ROOT_GROUP_ID.equals(g.id())) {
                continue;
            }
            if (Objects.equals(g.id(), g.parentId())) {
                throw new StructDefinitionException("The parent of Group can't be itself.");
            }
            QueryNode node = new QueryNode(g.id(), g.logic());
            // the latter covers the former
            queryNodeMap.put(g.id(), node);
            id2ParentIdMap.put(g.id(), g.parentId());
        }
        // set parent node
        queryNodeMap.values().forEach(node -> {
            // skip ROOT QueryNode
            if (QueryConst.DEFAULT_ROOT_GROUP_ID.equals(node.getGroupId())) {
                return ;
            }
            String parentGroupId = id2ParentIdMap.get(node.getGroupId());
            QueryNode parentNode = queryNodeMap.get(parentGroupId);
            if (parentNode == null) {
                parentNode = new QueryNode(parentGroupId, root);
            }
            node.setParent(parentNode);
            parentNode.addChild(node);
            queryNodeMap.putIfAbsent(parentGroupId, parentNode);
        });
        checkRingExists(root);
        for (QueryField field : fields) {
            Map<String, Map<Function<Object, Boolean>, List<QueryBlock>>> outerMap = Arrays.stream(field.getQueryColumns())
                    .collect(Collectors.groupingBy(QueryColumn::groupId,
                            Collectors.groupingBy(column -> column.check().getExpression(),
                                    Collectors.mapping(QueryBlock::convert, Collectors.toList()))));
            outerMap.forEach((groupId, innerMap) -> {
                QueryNode node = queryNodeMap.get(groupId);
                if (node == null) {
                    node = new QueryNode(groupId, root);
                }
                queryNodeMap.putIfAbsent(groupId, node);
                Map<Function<Object, Boolean>, List<QueryBlock>> blocksMap = node.getQueryBlocksMap().getOrDefault(field.getField(), new HashMap<>());
                blocksMap.putAll(innerMap);
                node.getQueryBlocksMap().putIfAbsent(field.getField(), blocksMap);
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
                throw new StructDefinitionException("There is a ring exists " +
                        "when building QueryTree for " + sourceClass.getName() + "." +
                        " GroupId are " + first.getGroupId() +
                        " and " + first.getParent().getGroupId() + ".");
            }
            if (CollectionUtils.isNotEmpty(first.getChildren())) {
                open.addAll(first.getChildren());
            }
        }
    }

}
