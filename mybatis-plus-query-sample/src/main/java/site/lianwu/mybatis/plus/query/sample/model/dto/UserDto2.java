package site.lianwu.mybatis.plus.query.sample.model.dto;

import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto2 {

    @Query
    private Long id;
    @Query(ConditionType.Ne.class)
    private Integer age;
    @Query(value = ConditionType.Gt.class, column = "birthday")
    private LocalDate minBirthday;
    @Query(value = ConditionType.Lt.class, column = "birthday")
    private LocalDate maxBirthday;
    @Query(value = ConditionType.NotLike.class, column = "email")
    private String notLikeEmail;
    @Query(value = ConditionType.LikeLeft.class, column = "email")
    private String likeLeftEmail;
    @Query(value = ConditionType.LikeRight.class, column = "email")
    private String likeRightEmail;

}
