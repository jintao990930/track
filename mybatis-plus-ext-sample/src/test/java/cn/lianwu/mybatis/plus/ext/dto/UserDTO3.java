package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.QueryCondition;
import cn.lianwu.mybatis.plus.ext.query.QueryConstants;
import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO3 {

    @QueryCondition(groupId = QueryConstants.GROUP_A)
    private Long id;

    @QueryCondition(value = Operator.LIKE, logic = Logic.OR, column = "email", groupId = QueryConstants.GROUP_B)
    @QueryCondition(value = Operator.LIKE, logic = Logic.OR, column = "name", groupId = QueryConstants.GROUP_B)
    private String keyword;

    @QueryCondition(value = Operator.GE, column = "birthday", groupId = QueryConstants.GROUP_A)
    private LocalDate minBirthday;
    @QueryCondition(value = Operator.LE, column = "birthday", groupId = QueryConstants.GROUP_A)
    private LocalDate maxBirthday;

}
