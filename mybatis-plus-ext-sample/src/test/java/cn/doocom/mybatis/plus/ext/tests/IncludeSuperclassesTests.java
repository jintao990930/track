package cn.doocom.mybatis.plus.ext.tests;

import cn.doocom.entity.User;
import cn.doocom.mapper.UserMapper;
import cn.doocom.mybatis.plus.ext.dto.UserDTO4;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperTemplate;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class IncludeSuperclassesTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void emptyConditionTest() {
        UserDTO4 userDTO4 = new UserDTO4();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO4, true);
        userMapper.selectList(wrapper);
    }

    @Test
    public void selfClassConditionTest() {
        UserDTO4 userDTO4 = new UserDTO4();
        userDTO4.setId(1L);
        userDTO4.setKeyword("Jone");
        userDTO4.setAge(17);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO4);
        userMapper.selectList(wrapper);
    }

    @Test
    public void includedSuperclassConditionTest() {
        UserDTO4 userDTO4 = new UserDTO4();
        userDTO4.setId(1L);
        userDTO4.setKeyword("Jone");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO4, true);
        userMapper.selectList(wrapper);
    }

    @Test
    public void fullOfConditionTest() {
        UserDTO4 userDTO4 = new UserDTO4();
        userDTO4.setId(1L);
        userDTO4.setKeyword("Jone");
        userDTO4.setStartDate(LocalDate.of(1999, 9, 30));
        userDTO4.setEndDate(LocalDate.of(2023, 5, 14));
        userDTO4.setAge(17);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO4, true);
        userMapper.selectList(wrapper);
    }

}
