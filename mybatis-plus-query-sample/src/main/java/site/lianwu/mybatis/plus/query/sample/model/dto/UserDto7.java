package site.lianwu.mybatis.plus.query.sample.model.dto;

import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto7 {

    @Query(ConditionType.Ge.class)
    private LocalDate birthday;

    @Query(ConditionType.Lt.class)
    private Integer age;

}
