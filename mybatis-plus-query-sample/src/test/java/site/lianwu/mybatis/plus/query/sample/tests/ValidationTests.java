package site.lianwu.mybatis.plus.query.sample.tests;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.lianwu.mybatis.plus.query.QueryWrappers;
import site.lianwu.mybatis.plus.query.sample.model.dto.UserDto;
import site.lianwu.mybatis.plus.query.sample.model.entity.User;
import site.lianwu.mybatis.plus.query.sample.service.UserService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class ValidationTests {

    @Resource
    private UserService userService;

    @Test
    public void testNullObject() {
        UserDto dto = new UserDto();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testEmptyString() {
        UserDto dto = new UserDto();
        dto.setName("");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testBlankString() {
        UserDto dto = new UserDto();
        dto.setEmail("       ");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testEmptyArray() {
        UserDto dto = new UserDto();
        dto.setNotInIds(new Long[] {});
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testEmptyCollection() {
        UserDto dto = new UserDto();
        dto.setInIds(Collections.emptyList());
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

}
