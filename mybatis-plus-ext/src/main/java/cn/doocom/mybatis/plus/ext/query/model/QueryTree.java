package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.QueryClass;
import cn.doocom.mybatis.plus.ext.query.QueryField;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryTree {

    private final Class<?> sourceClass;
    private final boolean includedSuperclasses;
    private final QueryNode root;

    public QueryTree(QueryClass queryClass) {
        sourceClass = queryClass.getSourceClass();
        includedSuperclasses = queryClass.isIncludedSuperclasses();
        root = buildTree(queryClass.getQueryFields());
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public boolean isIncludedSuperclasses() {
        return includedSuperclasses;
    }

    public QueryNode getRoot() {
        return root;
    }

    private QueryNode buildTree(List<QueryField> queryFields) {
        QueryNode root = new QueryNode(QueryConst.DEFAULT_ROOT_GROUP_ID);
        Map<String, QueryNode> queryNodeMap = new HashMap<>(8);
        queryNodeMap.put(QueryConst.DEFAULT_ROOT_GROUP_ID, root);
        for (QueryField qf : queryFields) {
            Map<String, Map<Function<Object, Boolean>, List<QueryBlock>>> outerMap = Arrays.stream(qf.getQueryColumns())
                    .collect(Collectors.groupingBy(QueryColumn::groupId,
                            Collectors.groupingBy(qc -> qc.check().getExpression(),
                                    Collectors.mapping(qc -> QueryBlock.convert(qf.getField(), qc),
                                            Collectors.toList()))));
            outerMap.forEach((groupId, innerMap) -> {
                QueryNode node = queryNodeMap.get(groupId);
                if (node == null) {
                    node = new QueryNode(groupId, root);
                }
                queryNodeMap.putIfAbsent(groupId, node);
                Map<Function<Object, Boolean>, List<QueryBlock>> blocksMap = node.getQueryBlocksMap().getOrDefault(qf.getField(), new HashMap<>(4));
                blocksMap.putAll(innerMap);
                node.getQueryBlocksMap().putIfAbsent(qf.getField(), blocksMap);
            });
        }
        return root;
    }

}
