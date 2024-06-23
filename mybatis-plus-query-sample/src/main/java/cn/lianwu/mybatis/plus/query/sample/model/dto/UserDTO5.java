package cn.lianwu.mybatis.plus.query.sample.model.dto;

import cn.lianwu.mybatis.plus.query.Query;
import cn.lianwu.mybatis.plus.query.function.ConditionType;
import lombok.Data;

@Data
public class UserDTO5 {

    @Query(ConditionType.Le.class)
    private Long id;

}
