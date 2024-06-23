package cn.lianwu.mybatis.plus.ext.tests;

import cn.lianwu.model.dto.UserDTO;
import cn.lianwu.model.entity.User;
import cn.lianwu.mybatis.plus.query.QueryWrappers;
import cn.lianwu.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class ValidationTests {

    @Resource
    private UserService userService;

    @Test
    public void nullObjectTest() {
        UserDTO dto = new UserDTO();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void emptyStringTest() {
        UserDTO dto = new UserDTO();
        dto.setName("");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void blankStringTest() {
        UserDTO dto = new UserDTO();
        dto.setEmail("       ");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void emptyArrayTest() {
        UserDTO dto = new UserDTO();
        dto.setNotInIds(new Long[] {});
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void emptyCollectionTest() {
        UserDTO dto = new UserDTO();
        dto.setInIds(Collections.emptyList());
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list();

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

}
