package cn.lianwu.mybatis.plus.query.sample.tests;

import cn.lianwu.mybatis.plus.query.sample.model.dto.UserDTO4;
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
public class IncludeInheritedFieldsTests {

    @Resource
    private UserService userService;

    @Test
    public void emptyConditionTest() {
        boolean includeInheritedFields = true;
        UserDTO4 dto = new UserDTO4();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, includeInheritedFields);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void notIncludeInheritedFieldsConditionTest() {
        boolean includeInheritedFields = false;
        UserDTO4 dto = new UserDTO4();
        dto.setId(1L);
        dto.setKeyword("Jone");
        dto.setAge(17);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void includeInheritedFieldsConditionTest() {
        boolean includeInheritedFields = true;
        UserDTO4 dto = new UserDTO4();
        dto.setId(1L);
        dto.setKeyword("Jone");
        dto.setMinBirthday(LocalDate.of(1999, 9, 30));
        dto.setMaxBirthday(LocalDate.of(2023, 5, 14));
        dto.setAge(17);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, includeInheritedFields);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

}
