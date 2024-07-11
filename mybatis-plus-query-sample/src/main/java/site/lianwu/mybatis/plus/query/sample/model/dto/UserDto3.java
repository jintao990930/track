package site.lianwu.mybatis.plus.query.sample.model.dto;

import lombok.Data;
import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import site.lianwu.mybatis.plus.query.function.Logic;
import site.lianwu.mybatis.plus.query.sample.consts.QueryGroupConstant;

import java.time.LocalDate;

@Data
public class UserDto3 {

    @Query(groupId = QueryGroupConstant.GROUP_A)
    private Long id;

    @Query(value = ConditionType.Like.class, column = "email", logic = Logic.OR, groupId = QueryGroupConstant.GROUP_B)
    @Query(value = ConditionType.Like.class, column = "name", logic = Logic.OR, groupId = QueryGroupConstant.GROUP_B)
    private String keyword;

    @Query(value = ConditionType.Ge.class, column = "birthday", groupId = QueryGroupConstant.GROUP_A)
    private LocalDate minBirthday;
    @Query(value = ConditionType.Le.class, column = "birthday", groupId = QueryGroupConstant.GROUP_A)
    private LocalDate maxBirthday;

}
