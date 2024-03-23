package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    @QueryColumn(Operator.LIKE)
    private String name;
    @QueryColumn(value = Operator.GE, column = "age")
    private Integer minAge;
    @QueryColumn(value = Operator.LE, column = "age")
    private Integer maxAge;
    @QueryColumn(value = Operator.IN, column = "id")
    private List<Long> inIds;
    @QueryColumn(value = Operator.NOT_IN, column = "id")
    private Long[] notInIds;

}
