package cn.lianwu.mybatis.plus.ext.tests;

import cn.lianwu.entity.User;
import cn.lianwu.mapper.UserMapper;
import cn.lianwu.mybatis.plus.ext.dto.UserDTO4;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.lianwu.mybatis.plus.ext.query.QueryWrapperTemplate;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class IncludeInheritedFieldsTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void emptyObjectValueTest() {
        UserDTO4 dto = new UserDTO4();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto, true);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto, true);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void notIncludeInheritedFieldsConditionTest() {
        UserDTO4 dto = new UserDTO4();
        dto.setId(1L);
        dto.setKeyword("Jone");
        dto.setAge(17);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto, false);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void includeInheritedFieldsConditionTest() {
        UserDTO4 dto = new UserDTO4();
        dto.setId(1L);
        dto.setKeyword("Jone");
        dto.setAge(17);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto, true);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto, true);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void fullObjectValueTest() {
        UserDTO4 dto = new UserDTO4();
        dto.setId(1L);
        dto.setKeyword("Jone");
        dto.setMinBirthday(LocalDate.of(1999, 9, 30));
        dto.setMaxBirthday(LocalDate.of(2023, 5, 14));
        dto.setAge(17);
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto, true);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto, true);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    private List<User> comparedUsers(UserDTO4 dto, boolean includeInheritedFields) {
        boolean setEqId = includeInheritedFields && Objects.nonNull(dto.getId());
        boolean geBirthday = includeInheritedFields && Objects.nonNull(dto.getMinBirthday());
        boolean leBirthday = includeInheritedFields && Objects.nonNull(dto.getMaxBirthday());
        boolean geAge = Objects.nonNull(dto.getAge());

        boolean likeEmail = includeInheritedFields && StringUtils.isNotBlank(dto.getKeyword());
        boolean likeName = includeInheritedFields && StringUtils.isNotBlank(dto.getKeyword());
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
