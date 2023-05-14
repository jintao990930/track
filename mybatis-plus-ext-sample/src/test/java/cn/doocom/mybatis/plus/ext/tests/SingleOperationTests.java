package cn.doocom.mybatis.plus.ext.tests;

import cn.doocom.entity.User;
import cn.doocom.mapper.UserMapper;
import cn.doocom.mybatis.plus.ext.dto.UserDTO;
import cn.doocom.mybatis.plus.ext.dto.UserDTO2;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperTemplate;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootTest
public class SingleOperationTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void likeTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("J");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void geTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAgeStart(20);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void leTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAgeEnd(20);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void inTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setInIds(Arrays.asList(1L, 3L));
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void notInTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setNotInIds(new Long[] {2L, 4L});
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void eqTest() {
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setId(3L);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

    @Test
    public void neTest() {
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setAge(18);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

    @Test
    public void gtTest() {
        LocalDate localDate = LocalDate.of(2002, 2, 16);
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setBirthdayStart(localDate);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

    @Test
    public void ltTest() {
        LocalDate localDate = LocalDate.of(2002, 2, 16);
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setBirthdayEnd(localDate);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

    @Test
    public void notLikeTest() {
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setNotLikeEmail("4@");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

    @Test
    public void likeLeftTest() {
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setLikeLeftEmail("com");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

    @Test
    public void likeRightTest() {
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setLikeRightEmail("test5");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

}
