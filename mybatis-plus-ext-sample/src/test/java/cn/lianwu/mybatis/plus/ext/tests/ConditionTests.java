package cn.lianwu.mybatis.plus.ext.tests;

import cn.lianwu.model.dto.UserDTO;
import cn.lianwu.model.dto.UserDTO2;
import cn.lianwu.model.entity.User;
import cn.lianwu.mybatis.plus.ext.query.QueryWrappers;
import cn.lianwu.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ConditionTests {

    @Resource
    private UserService userService;

    @Test
    public void eqTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setId(3L);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void neTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setAge(18);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void gtTest() {
        LocalDate localDate = LocalDate.of(2002, 2, 16);
        UserDTO2 dto = new UserDTO2();
        dto.setMinBirthday(localDate);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void geTest() {
        UserDTO dto = new UserDTO();
        dto.setMinAge(20);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void ltTest() {
        LocalDate maxBirthday = LocalDate.of(2002, 2, 16);
        UserDTO2 dto = new UserDTO2();
        dto.setMaxBirthday(maxBirthday);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void leTest() {
        UserDTO dto = new UserDTO();
        dto.setMaxAge(20);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void likeTest() {
        UserDTO dto = new UserDTO();
        dto.setName("J");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void notLikeTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setNotLikeEmail("4@");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void likeLeftTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setLikeLeftEmail("com");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void likeRightTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setLikeRightEmail("test5");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void inTest() {
        UserDTO dto = new UserDTO();
        dto.setInIds(Arrays.asList(1L, 3L));
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void notInTest() {
        UserDTO dto = new UserDTO();
        dto.setNotInIds(new Long[] {2L, 4L});
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void fullConditionTest1() {
        UserDTO dto = new UserDTO();
        dto.setName("an");
        dto.setEmail("test");
        dto.setMinAge(18);
        dto.setMaxAge(30);
        dto.setInIds(Arrays.asList(1L, 3L, 4L));
        dto.setNotInIds(new Long[] {2L, 3L, 5L});
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void fullConditionTest2() {
        UserDTO2 dto = new UserDTO2();
        dto.setId(3L);
        dto.setAge(23);
        dto.setMinBirthday(LocalDate.of(1995, 7, 13));
        dto.setMaxBirthday(LocalDate.of(2007, 9, 25));
        dto.setNotLikeEmail("126.com");
        dto.setLikeLeftEmail(".com");
        dto.setLikeRightEmail("test");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

}
