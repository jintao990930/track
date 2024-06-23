package cn.lianwu.mybatis.plus.query.sample.tests;

import cn.lianwu.mybatis.plus.query.sample.model.dto.UserDTO6;
import cn.lianwu.mybatis.plus.query.sample.model.dto.UserDTO7;
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
public class NestedQueryTests {

    @Resource
    private UserService userService;

    @Test
    public void emptyConditionTest() {
        boolean includeInheritedFields = true;
        UserDTO6 dto = new UserDTO6();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, includeInheritedFields);
        List<User> users = userService.list(wrapper);
        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void fullConditionTest() {
        boolean includeInheritedFields = true;
        UserDTO6 dto = new UserDTO6();
        dto.setId(5L);
        dto.setKeyword("an");
        UserDTO7 dto7 = new UserDTO7();
        dto7.setBirthday(LocalDate.of(2001, 1, 1));
        dto.setDto7(dto7);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, includeInheritedFields);
        List<User> users = userService.list(wrapper);
        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

}
