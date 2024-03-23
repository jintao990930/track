package cn.lianwu.mybatis.plus.ext.tests;

import cn.lianwu.entity.User;
import cn.lianwu.mapper.UserMapper;
import cn.lianwu.mybatis.plus.ext.dto.UserDTO;
import cn.lianwu.mybatis.plus.ext.dto.UserDTO2;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.lianwu.mybatis.plus.ext.query.QueryWrapperTemplate;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class SingleOperationTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void eqTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setId(3L);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void neTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setAge(18);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void gtTest() {
        LocalDate localDate = LocalDate.of(2002, 2, 16);
        UserDTO2 dto = new UserDTO2();
        dto.setMinBirthday(localDate);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void geTest() {
        UserDTO dto = new UserDTO();
        dto.setMinAge(20);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void ltTest() {
        LocalDate localDate = LocalDate.of(2002, 2, 16);
        UserDTO2 dto = new UserDTO2();
        dto.setMaxBirthday(localDate);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void leTest() {
        UserDTO dto = new UserDTO();
        dto.setMaxAge(20);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void likeTest() {
        UserDTO dto = new UserDTO();
        dto.setName("J");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void notLikeTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setNotLikeEmail("4@");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void likeLeftTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setLikeLeftEmail("com");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void likeRightTest() {
        UserDTO2 dto = new UserDTO2();
        dto.setLikeRightEmail("test5");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void inTest() {
        UserDTO dto = new UserDTO();
        dto.setInIds(Arrays.asList(1L, 3L));
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void notInTest() {
        UserDTO dto = new UserDTO();
        dto.setNotInIds(new Long[] {2L, 4L});
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    private List<User> comparedUsers(UserDTO dto) {
        return userMapper.selectList(Wrappers.<User>lambdaQuery()
                .like(StringUtils.isNotBlank(dto.getName()), User::getName, dto.getName())
                .ge(Objects.nonNull(dto.getMinAge()), User::getAge, dto.getMinAge())
                .le(Objects.nonNull(dto.getMaxAge()), User::getAge, dto.getMaxAge())
                .in(CollectionUtils.isNotEmpty(dto.getInIds()), User::getId, dto.getInIds())
                .notIn(dto.getNotInIds() != null && dto.getNotInIds().length > 0, User::getId, dto.getNotInIds()));
    }

    private List<User> comparedUsers(UserDTO2 dto) {
        return userMapper.selectList(Wrappers.<User>lambdaQuery()
                .eq(Objects.nonNull(dto.getId()), User::getId, dto.getId())
                .ne(Objects.nonNull(dto.getAge()), User::getAge, dto.getAge())
                .gt(Objects.nonNull(dto.getMinBirthday()), User::getBirthday, dto.getMinBirthday())
                .lt(Objects.nonNull(dto.getMaxBirthday()), User::getBirthday, dto.getMaxBirthday())
                .notLike(StringUtils.isNotBlank(dto.getNotLikeEmail()), User::getEmail, dto.getNotLikeEmail())
                .likeLeft(StringUtils.isNotBlank(dto.getLikeLeftEmail()), User::getEmail, dto.getLikeLeftEmail())
                .likeRight(StringUtils.isNotBlank(dto.getLikeRightEmail()), User::getEmail, dto.getLikeRightEmail()));
    }

}
