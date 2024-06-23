package cn.lianwu.model.dto;

import cn.lianwu.mybatis.plus.ext.query.Query;
import cn.lianwu.mybatis.plus.ext.query.function.ConditionType;
import lombok.Data;

@Data
public class UserDTO5 {

    @Query(ConditionType.Le.class)
    private Long id;

}
