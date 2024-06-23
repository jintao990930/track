package cn.lianwu.model.dto;

import cn.lianwu.consts.QueryGroupConstant;
import cn.lianwu.mybatis.plus.ext.query.NestedQuery;
import cn.lianwu.mybatis.plus.ext.query.Query;
import cn.lianwu.mybatis.plus.ext.query.function.ConditionType;
import cn.lianwu.mybatis.plus.ext.query.function.Logic;
import lombok.Data;

@Data
public class UserDTO6 extends UserDTO5 {

    @Query(value = ConditionType.Like.class, logic = Logic.OR, groupId = QueryGroupConstant.GROUP_A, column = "email")
    @Query(value = ConditionType.Like.class, logic = Logic.OR, groupId = QueryGroupConstant.GROUP_A, column = "name")
    private String keyword;

    @NestedQuery
    private UserDTO7 dto7;

}
