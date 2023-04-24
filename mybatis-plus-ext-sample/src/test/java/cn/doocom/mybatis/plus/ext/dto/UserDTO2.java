package cn.doocom.mybatis.plus.ext.dto;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO2 {

    @QueryColumn
    private Long id;
    @QueryColumn(Operator.NE)
    private Integer age;
    @QueryColumn(value = Operator.GT, column = "birthday")
    private LocalDate birthdayStart;
    @QueryColumn(value = Operator.LT, column = "birthday")
    private LocalDate birthdayEnd;
    @QueryColumn(value = Operator.NOT_LIKE, column = "email")
    private String notLikeEmail;
    @QueryColumn(value = Operator.LIKE_LEFT, column = "email")
    private String likeLeftEmail;
    @QueryColumn(value = Operator.LIKE_RIGHT, column = "email")
    private String likeRightEmail;

}
