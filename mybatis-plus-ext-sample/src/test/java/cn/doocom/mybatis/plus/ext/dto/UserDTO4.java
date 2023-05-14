package cn.doocom.mybatis.plus.ext.dto;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

@Data
public class UserDTO4 extends UserDTO3 {

    @QueryColumn(value = Operator.GE, groupId = QueryConst.BETA)
    private Integer age;

}
