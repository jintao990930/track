package cn.edu.zhku.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1691873956126863400L;

    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "gender", columnDefinition = "VARCHAR(10)")
    private String gender;

    @Column(name = "age", columnDefinition = "INT")
    private Integer age;

    @Column(name = "height", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal height;

    @Column(name = "weight", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal weight;

    @Column(name = "birthday", columnDefinition = "DATE")
    private Date birthday;

}
