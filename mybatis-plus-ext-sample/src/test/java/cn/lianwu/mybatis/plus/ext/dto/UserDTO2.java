package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.QueryCondition;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO2 {

    @QueryCondition
    private Long id;
    @QueryCondition(Operator.NE)
    private Integer age;
    @QueryCondition(value = Operator.GT, column = "birthday")
    private LocalDate minBirthday;
    @QueryCondition(value = Operator.LT, column = "birthday")
    private LocalDate maxBirthday;
    @QueryCondition(value = Operator.NOT_LIKE, column = "email")
    private String notLikeEmail;
    @QueryCondition(value = Operator.LIKE_LEFT, column = "email")
    private String likeLeftEmail;
    @QueryCondition(value = Operator.LIKE_RIGHT, column = "email")
    private String likeRightEmail;

}
