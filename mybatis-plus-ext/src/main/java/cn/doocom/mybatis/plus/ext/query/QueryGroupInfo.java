package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.enums.Logic;

import java.util.Objects;

public final class QueryGroupInfo {

    private final String id;
    private final Logic logic;

    public QueryGroupInfo(String id) {
        this(id, Logic.AND);
    }

    public QueryGroupInfo(String id, Logic logic) {
        assert Objects.nonNull(id);
        assert Objects.nonNull(logic);
        this.id = id;
        this.logic = logic;
    }

    public String getId() {
        return id;
    }

    public Logic getLogic() {
        return logic;
    }

}
