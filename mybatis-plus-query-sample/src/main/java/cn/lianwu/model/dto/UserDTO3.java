package cn.lianwu.model.dto;

import cn.lianwu.consts.QueryGroupConstant;
import cn.lianwu.mybatis.plus.query.Query;
import cn.lianwu.mybatis.plus.query.function.ConditionType;
import cn.lianwu.mybatis.plus.query.function.Logic;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO3 {

    @Query(groupId = QueryGroupConstant.GROUP_A)
    private Long id;

    @Query(value = ConditionType.Like.class, logic = Logic.OR, column = "email", groupId = QueryGroupConstant.GROUP_B)
    @Query(value = ConditionType.Like.class, logic = Logic.OR, column = "name", groupId = QueryGroupConstant.GROUP_B)
    private String keyword;

    @Query(value = ConditionType.Ge.class, column = "birthday", groupId = QueryGroupConstant.GROUP_A)
    private LocalDate minBirthday;
    @Query(value = ConditionType.Le.class, column = "birthday", groupId = QueryGroupConstant.GROUP_A)
    private LocalDate maxBirthday;

}
