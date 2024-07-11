package site.lianwu.mybatis.plus.query.sample.model.dto;

import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import site.lianwu.mybatis.plus.query.function.Validator;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    @Query(ConditionType.Like.class)
    private String name;
    @Query(value = ConditionType.LikeRight.class, validation = Validator.NotBlank.class)
    private String email;
    @Query(value = ConditionType.Ge.class, column = "age")
    private Integer minAge;
    @Query(value = ConditionType.Le.class, column = "age")
    private Integer maxAge;
    @Query(value = ConditionType.In.class, column = "id")
    private List<Long> inIds;
    @Query(value = ConditionType.NotIn.class, column = "id")
    private Long[] notInIds;

}
