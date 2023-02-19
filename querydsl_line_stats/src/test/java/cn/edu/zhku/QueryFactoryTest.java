package cn.edu.zhku;

import cn.edu.zhku.test.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class QueryFactoryTest {

    @Resource
    private JPAQueryFactory queryFactory;

    private final QUser qUser = QUser.user;

    @Test
    public void test1() {
    }

}
