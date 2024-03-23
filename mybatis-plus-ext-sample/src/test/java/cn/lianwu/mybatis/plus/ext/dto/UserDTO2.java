package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO2 {

    @QueryColumn
    private Long id;
    @QueryColumn(Operator.NE)
    private Integer age;
    @QueryColumn(value = Operator.GT, column = "birthday")
    private LocalDate minBirthday;
    @QueryColumn(value = Operator.LT, column = "birthday")
    private LocalDate maxBirthday;
    @QueryColumn(value = Operator.NOT_LIKE, column = "email")
    private String notLikeEmail;
    @QueryColumn(value = Operator.LIKE_LEFT, column = "email")
    private String likeLeftEmail;
    @QueryColumn(value = Operator.LIKE_RIGHT, column = "email")
    private String likeRightEmail;

}
