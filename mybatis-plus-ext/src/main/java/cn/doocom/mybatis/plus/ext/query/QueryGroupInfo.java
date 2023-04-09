package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.util.Objects;

public final class QueryGroupInfo {

    private final String id;
    private final String parentId;
    private final Logic logic;

    public QueryGroupInfo(String id) {
        this(id, QueryConst.DEFAULT_ROOT_GROUP_ID, Logic.AND);
    }

    public QueryGroupInfo(String id, String parentId) {
        this(id, parentId, Logic.AND);
    }

    public QueryGroupInfo(String id, Logic logic) {
        this(id, QueryConst.DEFAULT_ROOT_GROUP_ID, logic);
    }

    public QueryGroupInfo(String id, String parentId, Logic logic) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(parentId);
        Objects.requireNonNull(logic);
        this.id = id;
        this.parentId = parentId;
        this.logic = logic;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public Logic getLogic() {
        return logic;
    }

}
