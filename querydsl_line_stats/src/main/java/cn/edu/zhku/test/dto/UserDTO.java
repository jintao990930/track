package cn.edu.zhku.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserDTO {

    @Positive
    private Integer id;

    @Length(max = 50)
    private String name;

    @Length(max = 10)
    private String gender;

    private Integer age;

    @DecimalMax("999.99")
    private BigDecimal height;

    @DecimalMax("999.99")
    private BigDecimal weight;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

}
