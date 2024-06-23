package cn.lianwu.model.dto;

import cn.lianwu.mybatis.plus.ext.query.Query;
import cn.lianwu.mybatis.plus.ext.query.function.ConditionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO7 {

    @Query(ConditionType.Ge.class)
    private LocalDate birthday;

}
