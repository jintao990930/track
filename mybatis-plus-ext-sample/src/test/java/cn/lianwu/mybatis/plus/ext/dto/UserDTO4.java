package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.consts.QueryConsts;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

@Data
public class UserDTO4 extends UserDTO3 {

    @QueryColumn(value = Operator.GE, groupId = QueryConsts.ALPHA)
    private Integer age;

}
