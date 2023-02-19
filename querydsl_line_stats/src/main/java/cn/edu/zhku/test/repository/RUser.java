package cn.edu.zhku.test.repository;

import cn.edu.zhku.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RUser extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {
}
