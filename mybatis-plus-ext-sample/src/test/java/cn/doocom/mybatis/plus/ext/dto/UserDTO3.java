package cn.doocom.mybatis.plus.ext.dto;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.annotation.QueryGroup;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.time.LocalDate;

@Data
@QueryGroup(value = QueryConst.ALPHA, logic = Logic.OR)
@QueryGroup(value = QueryConst.BETA, parentId = QueryConst.ALPHA)
public class UserDTO3 {

    @QueryColumn
    private Long id;

    @QueryColumn(value = Operator.LIKE, column = "email", groupId = QueryConst.ALPHA)
    @QueryColumn(value = Operator.LIKE, column = "name", groupId = QueryConst.ALPHA)
    private String keyword;

    @QueryColumn(value = Operator.GE, column = "birthday", groupId = QueryConst.BETA)
    private LocalDate startDate;
    @QueryColumn(value = Operator.LE, column = "birthday", groupId = QueryConst.BETA)
    private LocalDate endDate;

}
