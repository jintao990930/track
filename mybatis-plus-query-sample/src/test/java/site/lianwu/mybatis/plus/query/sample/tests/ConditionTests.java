package site.lianwu.mybatis.plus.query.sample.tests;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.lianwu.mybatis.plus.query.QueryWrappers;
import site.lianwu.mybatis.plus.query.sample.model.dto.UserDto;
import site.lianwu.mybatis.plus.query.sample.model.dto.UserDto2;
import site.lianwu.mybatis.plus.query.sample.model.entity.User;
import site.lianwu.mybatis.plus.query.sample.service.UserService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ConditionTests {

    @Resource
    private UserService userService;

    @Test
    public void testEq() {
        UserDto2 dto = new UserDto2();
        dto.setId(3L);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void testNe() {
        UserDto2 dto = new UserDto2();
        dto.setAge(18);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testGt() {
        LocalDate localDate = LocalDate.of(2002, 2, 16);
        UserDto2 dto = new UserDto2();
        dto.setMinBirthday(localDate);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testGe() {
        UserDto dto = new UserDto();
        dto.setMinAge(20);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testLt() {
        LocalDate maxBirthday = LocalDate.of(2002, 2, 16);
        UserDto2 dto = new UserDto2();
        dto.setMaxBirthday(maxBirthday);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testLe() {
        UserDto dto = new UserDto();
        dto.setMaxAge(20);
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testLike() {
        UserDto dto = new UserDto();
        dto.setName("J");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void testNotLike() {
        UserDto2 dto = new UserDto2();
        dto.setNotLikeEmail("4@");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testLikeLeft() {
        UserDto2 dto = new UserDto2();
        dto.setLikeLeftEmail("com");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testLikeRight() {
        UserDto2 dto = new UserDto2();
        dto.setLikeRightEmail("test5");
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testIn() {
        UserDto dto = new UserDto();
        dto.setInIds(Arrays.asList(1L, 3L));
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testNotIn() {
        UserDto dto = new UserDto();
        dto.setNotInIds(new Long[] {2L, 4L});
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testFullCondition1() {
        UserDto dto = new UserDto();
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
    public void testFullCondition2() {
        UserDto2 dto = new UserDto2();
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
