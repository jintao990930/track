package cn.lianwu.mybatis.plus.ext.query.model;

import cn.lianwu.mybatis.plus.ext.query.QueryClass;
import cn.lianwu.mybatis.plus.ext.query.QueryField;
import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.consts.QueryConsts;

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
        QueryNode root = new QueryNode(QueryConsts.DEFAULT_ROOT_GROUP_ID);
        Map<String, QueryNode> groupId2QueryNodeMap = new HashMap<>(8);
        groupId2QueryNodeMap.put(QueryConsts.DEFAULT_ROOT_GROUP_ID, root);
        for (QueryField qf : queryFields) {
            Map<String, Map<Function<Object, Boolean>, List<QueryBlock>>> groupId2ConditionalQueryBlocksMap = Arrays.stream(qf.getQueryColumns())
                    .collect(Collectors.groupingBy(QueryColumn::groupId,
                            Collectors.groupingBy(qc -> qc.check().getExpression(),
                                    Collectors.mapping(qc -> QueryBlock.assemble(qf.getField(), qc),
                                            Collectors.toList()))));
            groupId2ConditionalQueryBlocksMap.forEach((groupId, conditionalQueryBlocks) -> {
                QueryNode node = groupId2QueryNodeMap.get(groupId);
                if (node == null) {
                    node = new QueryNode(groupId, root);
                }
                groupId2QueryNodeMap.put(groupId, node);
                Map<Function<Object, Boolean>, List<QueryBlock>> relatedConditionalQueryBlocks = node.getField2ConditionalQueryBlocksMap().getOrDefault(qf.getField(), new HashMap<>(4));
                relatedConditionalQueryBlocks.putAll(conditionalQueryBlocks);
                node.getField2ConditionalQueryBlocksMap().put(qf.getField(), relatedConditionalQueryBlocks);
            });
        }
        return root;
    }

}
