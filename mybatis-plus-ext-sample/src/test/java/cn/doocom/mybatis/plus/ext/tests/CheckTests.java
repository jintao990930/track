package cn.doocom.mybatis.plus.ext.tests;

import cn.doocom.entity.User;
import cn.doocom.mapper.UserMapper;
import cn.doocom.mybatis.plus.ext.dto.UserDTO;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperTemplate;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class CheckTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void emptyConditionTest() {
        UserDTO userDTO = new UserDTO();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void blankStringTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("     ");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void emptyArrayTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setNotInIds(new Long[] {});
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void emptyCollectionTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setInIds(Collections.emptyList());
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

}
