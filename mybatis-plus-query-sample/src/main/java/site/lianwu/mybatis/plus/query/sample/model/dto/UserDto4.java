package site.lianwu.mybatis.plus.query.sample.model.dto;

import site.lianwu.mybatis.plus.query.sample.consts.QueryGroupConstant;
import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import lombok.Data;

@Data
public class UserDto4 extends UserDto3 {

    @Query(value = ConditionType.Ge.class, groupId = QueryGroupConstant.GROUP_A)
    private Integer age;

}
