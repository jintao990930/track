package cn.doocom.mybatis.plus.ext.tests;

import cn.doocom.entity.User;
import cn.doocom.mapper.UserMapper;
import cn.doocom.mybatis.plus.ext.dto.UserDTO3;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperTemplate;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class MultiGroupTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void emptyConditionTest() {
        UserDTO3 userDTO3 = new UserDTO3();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO3);
        userMapper.selectList(wrapper);
    }

    @Test
    public void partOfConditionTest() {
        UserDTO3 userDTO3 = new UserDTO3();
        userDTO3.setId(1L);
        userDTO3.setKeyword("t");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO3);
        userMapper.selectList(wrapper);
    }

    @Test
    public void fullOfConditionTest() {
        UserDTO3 userDTO3 = new UserDTO3();
        userDTO3.setId(1L);
        userDTO3.setKeyword("t");
        userDTO3.setStartDate(LocalDate.of(1999, 9, 30));
        userDTO3.setEndDate(LocalDate.of(2023, 5, 14));
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO3);
        userMapper.selectList(wrapper);
    }

}
