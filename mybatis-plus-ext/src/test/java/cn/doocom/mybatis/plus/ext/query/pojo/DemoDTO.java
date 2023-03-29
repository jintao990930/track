package cn.doocom.mybatis.plus.ext.query.pojo;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
import cn.doocom.mybatis.plus.ext.query.annotation.Validation;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;

@QueryGroup(id = QueryConst.MAIN_GROUP_ID)
public class DemoDTO {

//    @QueryColumn
//    @QueryColumn
    @Validation
    private String keyWord;

    private Long id;

    private String name;

}
