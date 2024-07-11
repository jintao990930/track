package site.lianwu.mybatis.plus.query.sample.tests;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.lianwu.mybatis.plus.query.QueryWrappers;
import site.lianwu.mybatis.plus.query.sample.model.dto.UserDto4;
import site.lianwu.mybatis.plus.query.sample.model.entity.User;
import site.lianwu.mybatis.plus.query.sample.service.UserService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class IncludeInheritedFieldsTests {

    @Resource
    private UserService userService;

    @Test
    public void testEmptyCondition() {
        boolean includeInheritedFields = true;
        UserDto4 dto = new UserDto4();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, includeInheritedFields);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void testNotIncludeInheritedFieldsCondition() {
        boolean includeInheritedFields = false;
        UserDto4 dto = new UserDto4();
        dto.setId(1L);
        dto.setKeyword("Jone");
        dto.setAge(17);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void testIncludeInheritedFieldsCondition() {
        boolean includeInheritedFields = true;
        UserDto4 dto = new UserDto4();
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
