package cn.doocom.mybatis.plus.ext.dto;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO3 {

    @QueryColumn(groupId = QueryConst.ALPHA)
    private Long id;

    @QueryColumn(value = Operator.LIKE, logic = Logic.OR, column = "email", groupId = QueryConst.BETA)
    @QueryColumn(value = Operator.LIKE, logic = Logic.OR, column = "name", groupId = QueryConst.BETA)
    private String keyword;

    @QueryColumn(value = Operator.GE, column = "birthday", groupId = QueryConst.ALPHA)
    private LocalDate startDate;
    @QueryColumn(value = Operator.LE, column = "birthday", groupId = QueryConst.ALPHA)
    private LocalDate endDate;

}
