package cn.doocom.mybatis.plus.ext.query;

import cn.doocom.mybatis.plus.ext.query.enums.Logic;

public class QueryGroupInfo {

    private String id;
    private Logic logic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

}
