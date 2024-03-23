package cn.lianwu.mybatis.plus.ext.tests;

import cn.lianwu.entity.User;
import cn.lianwu.mapper.UserMapper;
import cn.lianwu.mybatis.plus.ext.dto.UserDTO4;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.lianwu.mybatis.plus.ext.query.QueryOption;
import cn.lianwu.mybatis.plus.ext.query.QueryWrapperTemplate;
import cn.lianwu.mybatis.plus.ext.query.consts.QueryConsts;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

@SpringBootTest
public class PostProcessorTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void emptyPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor(w -> {}).build();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto, option);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto, false);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void notExistedPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor("lianwu", w -> {
                    w.lambda().eq(User::getId, -1);
                }).build();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto, option);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto, false);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void withPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor(w -> {
                    w.lambda().gt(User::getAge, 17);
                })
                .withPostProcessor(QueryConsts.BETA, w -> {
                    w.lambda().eq(User::getId, 3);
                }).build();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto, true, option);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = userMapper.selectList(Wrappers.<User>lambdaQuery()
                .ge(User::getAge, 17)
                .and(w -> w.eq(User::getId, 3)));

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void withMultiPostProcessorTest() {
        UserDTO4 dto = new UserDTO4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withPostProcessor(QueryConsts.BETA, w -> {
                    w.lambda().gt(User::getAge, 17);
                })
                .withPostProcessor(QueryConsts.BETA, w -> {
                    w.lambda().eq(User::getId, 3);
                }).build();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto, true, option);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = userMapper.selectList(Wrappers.<User>lambdaQuery()
                .and(w -> w.gt(User::getAge, 17)
                        .eq(User::getId, 3)));

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    private List<User> comparedUsers(UserDTO4 dto, boolean includedSuperclasses) {
        boolean setEqId = includedSuperclasses && Objects.nonNull(dto.getId());
        boolean geBirthday = includedSuperclasses && Objects.nonNull(dto.getMinBirthday());
        boolean leBirthday = includedSuperclasses && Objects.nonNull(dto.getMaxBirthday());
        boolean geAge = Objects.nonNull(dto.getAge());

        boolean likeEmail = includedSuperclasses && StringUtils.isNotBlank(dto.getKeyword());
        boolean likeName = includedSuperclasses && StringUtils.isNotBlank(dto.getKeyword());
        return userMapper.selectList(Wrappers.<User>lambdaQuery()
                .and(setEqId || geBirthday || leBirthday || geAge, w -> {
                    w.eq(setEqId, User::getId, dto.getId())
                            .ge(geBirthday, User::getBirthday, dto.getMinBirthday())
                            .le(leBirthday, User::getBirthday, dto.getMaxBirthday())
                            .ge(geAge, User::getAge, dto.getAge());
                })
                .and(likeEmail || likeName, w -> {
                    w.like(likeEmail, User::getEmail, dto.getKeyword())
                            .or()
                            .like(likeName, User::getName, dto.getKeyword());
                }));
    }

}
