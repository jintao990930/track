package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.QueryCondition;
import cn.lianwu.mybatis.plus.ext.query.QueryConstants;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

@Data
public class UserDTO4 extends UserDTO3 {

    @QueryCondition(value = Operator.GE, groupId = QueryConstants.GROUP_A)
    private Integer age;

}
