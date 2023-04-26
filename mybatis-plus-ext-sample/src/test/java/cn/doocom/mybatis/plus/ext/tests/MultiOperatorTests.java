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
public class MultiOperatorTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("an");
        userDTO.setAgeStart(18);
        userDTO.setAgeEnd(30);
        userDTO.setInIds(Arrays.asList(1L, 3L, 4L));
        userDTO.setNotInIds(new Long[] {2L, 3L, 5L});
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO);
        userMapper.selectList(wrapper);
    }

    @Test
    public void test2() {
        LocalDate birthdayStart = LocalDate.of(1995, 7, 13);
        LocalDate birthdayEnd = LocalDate.of(2007, 9, 25);
        UserDTO2 userDTO2 = new UserDTO2();
        userDTO2.setId(3L);
        userDTO2.setAge(23);
        userDTO2.setBirthdayStart(birthdayStart);
        userDTO2.setBirthdayEnd(birthdayEnd);
        userDTO2.setNotLikeEmail("126.com");
        userDTO2.setLikeLeftEmail(".com");
        userDTO2.setLikeRightEmail("test");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO2);
        userMapper.selectList(wrapper);
    }

}
