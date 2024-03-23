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
public class MultiOperationTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void fullObjectValueTest1() {
        UserDTO dto = new UserDTO();
        dto.setName("an");
        dto.setMinAge(18);
        dto.setMaxAge(30);
        dto.setInIds(Arrays.asList(1L, 3L, 4L));
        dto.setNotInIds(new Long[] {2L, 3L, 5L});
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void fullObjectValueTest2() {
        UserDTO2 dto = new UserDTO2();
        dto.setId(3L);
        dto.setAge(23);
        dto.setMinBirthday(LocalDate.of(1995, 7, 13));
        dto.setMaxBirthday(LocalDate.of(2007, 9, 25));
        dto.setNotLikeEmail("126.com");
        dto.setLikeLeftEmail(".com");
        dto.setLikeRightEmail("test");
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
