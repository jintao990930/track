package cn.doocom.mybatis.plus.ext.query.struct;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.exception.GroupNotFoundException;
import cn.doocom.mybatis.plus.ext.query.exception.StructDefinitionException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryTree {

    private final Class<?> clazz;
    private final boolean includeInheritedFields;
    private final QueryNode root;
    private final Map<String, QueryNode> queryNodeMap;

    public QueryTree(QueryClass queryClass) {
        clazz = queryClass.getClazz();
        includeInheritedFields = queryClass.isIncludeInheritedFields();
        queryNodeMap = new HashMap<>(4);
        root = buildTree(queryClass.getQueryGroups(), queryClass.getQueryFields());
    }

    public void withPostProcessor(String groupId, Consumer<QueryWrapper<?>> processor) {
        QueryNode queryNode = queryNodeMap.get(groupId);
        if (Objects.isNull(queryNode)) {
            throw new GroupNotFoundException("Unable to find Group with id " + groupId + ".");
        }
        queryNode.addPostProcessor(processor);
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

    public Map<String, QueryNode> getQueryNodeMap() {
        return queryNodeMap;
    }

    private QueryNode buildTree(QueryGroup[] groups, List<QueryField> fields) {
        QueryNode root = new QueryNode(QueryConst.DEFAULT_ROOT_GROUP_ID);
        queryNodeMap.put(QueryConst.DEFAULT_ROOT_GROUP_ID, root);
        Map<String, String> id2ParentIdMap = new HashMap<>();
        for (QueryGroup g : groups) {
            // skip ROOT_GROUP_ID
            if (Objects.equals(g.id(), QueryConst.DEFAULT_ROOT_GROUP_ID)) {
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
            if (Objects.equals(node.getGroupId(), QueryConst.DEFAULT_ROOT_GROUP_ID)) {
                return ;
            }
            String parentGroupId = id2ParentIdMap.get(node.getGroupId());
            QueryNode parentNode = queryNodeMap.get(parentGroupId);
            if (Objects.isNull(parentNode)) {
                parentNode = new QueryNode(parentGroupId, root);
            }
            node.setParent(parentNode);
            parentNode.addChild(node);
            queryNodeMap.putIfAbsent(parentGroupId, parentNode);
        });
        checkRingExists(root);
        for (QueryField field : fields) {
            Map<String, Map<Function<Object, Boolean>, List<WhereBlock>>> outerMap = Arrays.stream(field.getQueryColumns())
                    .collect(Collectors.groupingBy(QueryColumn::groupId,
                            Collectors.groupingBy(column -> column.check().getExpression(),
                                    Collectors.mapping(WhereBlock::convert, Collectors.toList()))));
            outerMap.forEach((groupId, innerMap) -> {
                QueryNode node = queryNodeMap.get(groupId);
                if (Objects.isNull(node)) {
                    node = new QueryNode(groupId, root);
                }
                queryNodeMap.putIfAbsent(groupId, node);
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
                throw new StructDefinitionException("There is a ring exists " +
                        "when building QueryTree for " + clazz.getName() + "." +
                        " GroupId are " + first.getGroupId() +
                        " and " + first.getParent().getGroupId() + ".");
            }
            open.addAll(first.getChildren());
        }
    }

}