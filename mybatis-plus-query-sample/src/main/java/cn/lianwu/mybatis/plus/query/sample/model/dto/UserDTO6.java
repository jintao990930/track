package cn.lianwu.mybatis.plus.query.sample.model.dto;

import cn.lianwu.mybatis.plus.query.sample.consts.QueryGroupConstant;
import cn.lianwu.mybatis.plus.query.NestedQuery;
import cn.lianwu.mybatis.plus.query.Query;
import cn.lianwu.mybatis.plus.query.function.ConditionType;
import cn.lianwu.mybatis.plus.query.function.Logic;
import lombok.Data;

@Data
public class UserDTO6 extends UserDTO5 {

    @Query(value = ConditionType.Like.class, logic = Logic.OR, groupId = QueryGroupConstant.GROUP_A, column = "email")
    @Query(value = ConditionType.Like.class, logic = Logic.OR, groupId = QueryGroupConstant.GROUP_A, column = "name")
    private String keyword;

    @NestedQuery
    private UserDTO7 dto7;

}
