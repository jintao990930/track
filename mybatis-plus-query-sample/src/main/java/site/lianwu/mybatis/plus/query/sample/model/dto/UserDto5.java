package site.lianwu.mybatis.plus.query.sample.model.dto;

import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import lombok.Data;

@Data
public class UserDto5 {

    @Query(ConditionType.Le.class)
    private Long id;

}
