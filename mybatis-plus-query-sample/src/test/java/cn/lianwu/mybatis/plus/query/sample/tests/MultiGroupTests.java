package cn.lianwu.mybatis.plus.query.sample.tests;

import cn.lianwu.mybatis.plus.query.sample.model.dto.UserDTO3;
import cn.lianwu.mybatis.plus.query.sample.model.entity.User;
import cn.lianwu.mybatis.plus.query.QueryWrappers;
import cn.lianwu.mybatis.plus.query.sample.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class MultiGroupTests {

    @Resource
    private UserService userService;

    @Test
    public void emptyConditionTest() {
        UserDTO3 dto = new UserDTO3();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void fullConditionTest() {
        UserDTO3 dto = new UserDTO3();
        dto.setId(1L);
        dto.setKeyword("t");
        dto.setMinBirthday(LocalDate.of(1999, 9, 30));
        dto.setMaxBirthday(LocalDate.of(2023, 5, 14));
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

}
