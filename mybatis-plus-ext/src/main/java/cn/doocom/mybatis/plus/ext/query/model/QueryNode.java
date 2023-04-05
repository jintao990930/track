package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.QueryColumnInfo;
import cn.doocom.mybatis.plus.ext.query.QueryGroupInfo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class QueryNode {

    private QueryGroupInfo groupInfo;
    private Map<Field, List<QueryColumnInfo>> columnInfoMap;
    private List<QueryNode> nodes;

}
