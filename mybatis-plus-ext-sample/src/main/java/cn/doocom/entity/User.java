package cn.doocom.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private LocalDate birthday;
}
