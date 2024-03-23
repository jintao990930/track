package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.consts.QueryConsts;
import cn.lianwu.mybatis.plus.ext.query.enums.Logic;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO3 {

    @QueryColumn(groupId = QueryConsts.ALPHA)
    private Long id;

    @QueryColumn(value = Operator.LIKE, logic = Logic.OR, column = "email", groupId = QueryConsts.BETA)
    @QueryColumn(value = Operator.LIKE, logic = Logic.OR, column = "name", groupId = QueryConsts.BETA)
    private String keyword;

    @QueryColumn(value = Operator.GE, column = "birthday", groupId = QueryConsts.ALPHA)
    private LocalDate minBirthday;
    @QueryColumn(value = Operator.LE, column = "birthday", groupId = QueryConsts.ALPHA)
    private LocalDate maxBirthday;

}
