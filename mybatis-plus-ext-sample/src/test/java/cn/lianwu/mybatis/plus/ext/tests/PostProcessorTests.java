package cn.lianwu.mybatis.plus.ext.tests;

import cn.lianwu.consts.QueryGroupConstant;
import cn.lianwu.model.dto.UserDTO4;
import cn.lianwu.model.entity.User;
import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.QueryWrappers;
import cn.lianwu.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class PostProcessorTests {

    @Resource
    private UserService userService;

    @Test
    public void emptyPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor(w -> {}).build();
        QueryWrapper<User> wrapper = QueryWrappers.parse(dto, option);
        List<User> users = userService.list(wrapper);

        List<User> comparedUsers = userService.listUsers(dto, false);

        assert users.size() == comparedUsers.size() && users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void notExistedPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
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
    public void withMultiPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
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
    public void withRepeatedPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
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
