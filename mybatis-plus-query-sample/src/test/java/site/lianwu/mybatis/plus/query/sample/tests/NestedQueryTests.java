package site.lianwu.mybatis.plus.query.sample.tests;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.lianwu.mybatis.plus.query.QueryWrappers;
import site.lianwu.mybatis.plus.query.sample.model.dto.UserDto6;
import site.lianwu.mybatis.plus.query.sample.model.dto.UserDto7;
import site.lianwu.mybatis.plus.query.sample.model.entity.User;
import site.lianwu.mybatis.plus.query.sample.service.UserService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class NestedQueryTests {

    @Resource
    private UserService userService;

    @Test
    public void testEmptyCondition() {
        boolean includeInheritedFields = true;
        UserDto6 dto = new UserDto6();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, includeInheritedFields);
        List<User> users = userService.list(wrapper);
        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void testFullCondition() {
        boolean includeInheritedFields = true;
        UserDto6 dto = new UserDto6();
        dto.setId(5L);
        dto.setKeyword("an");
        UserDto7 dto7 = new UserDto7();
        dto7.setBirthday(LocalDate.of(2001, 1, 1));
        dto7.setAge(25);
        dto.setDto7(dto7);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, includeInheritedFields);
        List<User> users = userService.list(wrapper);
        List<User> comparedUsers = userService.listUsers(dto, includeInheritedFields);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

}
