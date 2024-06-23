package cn.lianwu.model.dto;

import cn.lianwu.mybatis.plus.ext.query.Query;
import cn.lianwu.mybatis.plus.ext.query.function.ConditionType;
import cn.lianwu.mybatis.plus.ext.query.function.Validator;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

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
