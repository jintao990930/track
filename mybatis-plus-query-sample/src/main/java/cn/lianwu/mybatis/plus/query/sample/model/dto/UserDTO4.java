package cn.lianwu.mybatis.plus.query.sample.model.dto;

import cn.lianwu.mybatis.plus.query.sample.consts.QueryGroupConstant;
import cn.lianwu.mybatis.plus.query.Query;
import cn.lianwu.mybatis.plus.query.function.ConditionType;
import lombok.Data;

@Data
public class UserDTO4 extends UserDTO3 {

    @Query(value = ConditionType.Ge.class, groupId = QueryGroupConstant.GROUP_A)
    private Integer age;

}
