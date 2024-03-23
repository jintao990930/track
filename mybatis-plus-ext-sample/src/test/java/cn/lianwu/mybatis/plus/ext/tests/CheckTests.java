package cn.lianwu.mybatis.plus.ext.tests;

import cn.lianwu.entity.User;
import cn.lianwu.mapper.UserMapper;
import cn.lianwu.mybatis.plus.ext.dto.UserDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.lianwu.mybatis.plus.ext.query.QueryWrapperTemplate;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

@SpringBootTest
public class CheckTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void emptyObjectValueTest() {
        UserDTO dto = new UserDTO();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);

    }

    @Test
    public void blankStringTest() {
        UserDTO dto = new UserDTO();
        dto.setName("     ");
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void emptyArrayTest() {
        UserDTO dto = new UserDTO();
        dto.setNotInIds(new Long[] {});
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    @Test
    public void emptyCollectionTest() {
        UserDTO dto = new UserDTO();
        dto.setInIds(Collections.emptyList());
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(dto);
        List<User> users = userMapper.selectList(wrapper);

        List<User> comparedUsers = comparedUsers(dto);

        assert users.size() == comparedUsers.size();

        assert users.containsAll(comparedUsers) && comparedUsers.containsAll(users);
    }

    private List<User> comparedUsers(UserDTO dto) {
        return userMapper.selectList(Wrappers.emptyWrapper());
    }

}
