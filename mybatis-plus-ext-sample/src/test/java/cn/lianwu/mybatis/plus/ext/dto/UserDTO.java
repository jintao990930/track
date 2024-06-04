package cn.lianwu.mybatis.plus.ext.dto;

import cn.lianwu.mybatis.plus.ext.query.QueryCondition;
import cn.lianwu.mybatis.plus.ext.query.enums.Operator;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    @QueryCondition(Operator.LIKE)
    private String name;
    @QueryCondition(value = Operator.GE, column = "age")
    private Integer minAge;
    @QueryCondition(value = Operator.LE, column = "age")
    private Integer maxAge;
    @QueryCondition(value = Operator.IN, column = "id")
    private List<Long> inIds;
    @QueryCondition(value = Operator.NOT_IN, column = "id")
    private Long[] notInIds;

}
