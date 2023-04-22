package cn.doocom.mybatis.plus.ext.dto;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    @QueryColumn(Operator.LIKE)
    private String name;
    @QueryColumn(value = Operator.GE, column = "age")
    private Integer ageStart;
    @QueryColumn(value = Operator.LE, column = "age")
    private Integer ageEnd;
    @QueryColumn(value = Operator.IN, column = "id")
    private List<Long> inIds;
    @QueryColumn(value = Operator.NOT_IN, column = "id")
    private Long[] notInIds;

}
