package cn.doocom.mybatis.plus.ext.tests;

import cn.doocom.entity.User;
import cn.doocom.mapper.UserMapper;
import cn.doocom.mybatis.plus.ext.dto.UserDTO4;
import cn.doocom.mybatis.plus.ext.query.QueryOption;
import cn.doocom.mybatis.plus.ext.query.QueryWrapperTemplate;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostProcessorTests {

    @Autowired
    private QueryWrapperTemplate queryWrapperTemplate;

    @Autowired
    private UserMapper userMapper;
    
    @Test
    public void emptyConditionTest() {
        UserDTO4 userDTO4 = new UserDTO4();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO4);
        userMapper.selectList(wrapper);
    }

    @Test
    public void emptyPostProcessorTest() {
        UserDTO4 userDTO4 = new UserDTO4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withProcessor(w -> {})
                .withProcessor(QueryConst.BETA, w -> {}).build();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO4, option);
        userMapper.selectList(wrapper);
    }

    @Test
    public void withPostProcessorTest() {
        UserDTO4 userDTO4 = new UserDTO4();
        QueryOption<User> option = QueryOption.<User>builder()
                .withProcessor(w -> {
                    w.lambda().gt(User::getAge, 17);
                })
                .withProcessor(QueryConst.BETA, w -> {
                    w.lambda().eq(User::getId, 3);
                }).build();
        QueryWrapper<User> wrapper = queryWrapperTemplate.parse(userDTO4, true, option);
        userMapper.selectList(wrapper);
    }

}
