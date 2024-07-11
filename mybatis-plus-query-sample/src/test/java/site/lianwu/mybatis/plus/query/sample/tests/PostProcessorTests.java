package site.lianwu.mybatis.plus.query.sample.tests;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.lianwu.mybatis.plus.query.QueryOption;
import site.lianwu.mybatis.plus.query.QueryWrappers;
import site.lianwu.mybatis.plus.query.sample.consts.QueryGroupConstant;
import site.lianwu.mybatis.plus.query.sample.model.dto.UserDto4;
import site.lianwu.mybatis.plus.query.sample.model.entity.User;
import site.lianwu.mybatis.plus.query.sample.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class PostProcessorTests {

    @Resource
    private UserService userService;

    @Test
    public void testEmptyPostProcessor() {
        UserDto4 dto = new UserDto4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor(w -> {}).build();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, option);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, false);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void testNotExistedPostProcessor() {
        UserDto4 dto = new UserDto4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor("lianwu", w -> {
                    w.lambda().eq(User::getId, -1);
                }).build();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, option);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, false);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testWithMultiPostProcessor() {
        UserDto4 dto = new UserDto4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor(w -> {
                    w.lambda().gt(User::getAge, 17);
                })
                .withPostProcessor(QueryGroupConstant.GROUP_B, w -> {
                    w.lambda().eq(User::getId, 3);
                }).build();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, true, option);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list(Wrappers.<User>lambdaQuery()
                .ge(User::getAge, 17)
                .and(w -> w.eq(User::getId, 3)));

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void testWithRepeatedPostProcessor() {
        UserDto4 dto = new UserDto4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor(QueryGroupConstant.GROUP_B, w -> {
                    w.lambda().gt(User::getAge, 17);
                })
                .withPostProcessor(QueryGroupConstant.GROUP_B, w -> {
                    w.lambda().eq(User::getId, 3);
                }).build();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, true, option);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.list(Wrappers.<User>lambdaQuery()
                .and(w -> w.gt(User::getAge, 17)
                        .eq(User::getId, 3)));

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

}
