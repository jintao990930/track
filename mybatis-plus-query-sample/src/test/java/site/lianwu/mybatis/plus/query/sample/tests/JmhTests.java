package site.lianwu.mybatis.plus.query.sample.tests;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import site.lianwu.mybatis.plus.query.Query;
import site.lianwu.mybatis.plus.query.QueryWrappers;
import site.lianwu.mybatis.plus.query.function.ConditionType;
import site.lianwu.mybatis.plus.query.function.Validator;
import site.lianwu.mybatis.plus.query.sample.model.entity.User;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

// 指定显示结果
@BenchmarkMode(Mode.AverageTime)
// 变量共享范围
@State(Scope.Thread)
// 预热轮次，每轮时间
@Warmup(iterations = 5, time = 1)
// 启动多少个JVM实例
@Fork(1)
// 指定显示结果单位
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// 测试轮次
@Measurement(iterations = 10)
public class JmhTests {

    static UserDto dto;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .build();
        new Runner(options).run();
    }

    @Setup
    public void setUp() {
        dto = UserDto.builder()
                .id(2L)
                .name("Jack")
                .age(24)
                .email(".com")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
    }

    @Benchmark
    public void testQueryWrappers(Blackhole blackhole) {
        blackhole.consume(QueryWrappers.parse(dto));
    }

    @Benchmark
    public void testManualSetting(Blackhole blackhole)  {
        blackhole.consume(condition(dto));
    }

    private QueryWrapper<User> condition(UserDto dto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(dto.getId() != null, "id", dto.getId())
                .like(StringUtils.isNotBlank(dto.getName()), "name", dto.getName())
                .le(dto.getAge() != null, "age", dto.getAge())
                .likeLeft(StringUtils.isNotBlank(dto.getEmail()), "email", dto.getEmail())
                .ge(dto.getBirthday() != null, "birthday", dto.getBirthday());
        return queryWrapper;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserDto {

        @Query
        private Long id;

        @Query(value = ConditionType.Like.class, validation = Validator.NotBlank.class)
        private String name;

        @Query(value = ConditionType.Le.class)
        private Integer age;

        @Query(value = ConditionType.LikeLeft.class, validation = Validator.NotBlank.class)
        private String email;

        @Query(value = ConditionType.Ge.class)
        private LocalDate birthday;

    }

}