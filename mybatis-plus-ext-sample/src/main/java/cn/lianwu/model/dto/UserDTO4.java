package cn.lianwu.model.dto;

import cn.lianwu.consts.QueryGroupConstant;
import cn.lianwu.mybatis.plus.ext.query.Query;
import cn.lianwu.mybatis.plus.ext.query.function.ConditionType;
import lombok.Data;

@Data
public class UserDTO4 extends UserDTO3 {

    @Query(value = ConditionType.Ge.class, groupId = QueryGroupConstant.GROUP_A)
    private Integer age;

}
