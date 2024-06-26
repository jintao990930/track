package cn.lianwu.mybatis.plus.query.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.lianwu.mybatis.plus.query.sample")
public class MyBatisPlusExtSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusExtSampleApplication.class, args);
    }

}
