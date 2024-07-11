package site.lianwu.mybatis.plus.query.sample.model.dto;

import lombok.Data;
import site.lianwu.mybatis.plus.query.NestedQuery;
import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import site.lianwu.mybatis.plus.query.function.Logic;
import site.lianwu.mybatis.plus.query.sample.consts.QueryGroupConstant;

@Data
public class UserDto6 extends UserDto5 {

    @Query(value = ConditionType.Like.class, logic = Logic.OR, groupId = QueryGroupConstant.GROUP_A, column = "email")
    @Query(value = ConditionType.Like.class, logic = Logic.OR, groupId = QueryGroupConstant.GROUP_A, column = "name")
    private String keyword;

    @NestedQuery
    private UserDto7 dto7;

}
