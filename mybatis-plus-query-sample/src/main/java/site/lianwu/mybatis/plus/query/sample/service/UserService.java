package site.lianwu.mybatis.plus.query.sample.service;

import site.lianwu.mybatis.plus.query.sample.model.dto.*;
import site.lianwu.mybatis.plus.query.sample.model.entity.User;
import site.lianwu.mybatis.plus.query.sample.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService extends ServiceImpl<UserMapper, User> {

    private final UserMapper userMapper;

    public List<User> listUsers(UserDto dto) {
        return list(Wrappers.<User>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getName()), User::getName, dto.getName())
                .likeRight(StringUtils.isNotBlank(dto.getEmail()), User::getEmail, dto.getEmail())
                .ge(Objects.nonNull(dto.getMinAge()), User::getAge, dto.getMinAge())
                .le(Objects.nonNull(dto.getMaxAge()), User::getAge, dto.getMaxAge())
                .in(CollectionUtils.isNotEmpty(dto.getInIds()), User::getId, dto.getInIds())
                .notIn(dto.getNotInIds() != null && dto.getNotInIds().length > 0, User::getId, dto.getNotInIds()));
    }

    public List<User> listUsers(UserDto2 dto) {
        return list(Wrappers.<User>lambdaQuery()
                .eq(Objects.nonNull(dto.getId()), User::getId, dto.getId())
                .ne(Objects.nonNull(dto.getAge()), User::getAge, dto.getAge())
                .gt(Objects.nonNull(dto.getMinBirthday()), User::getBirthday, dto.getMinBirthday())
                .lt(Objects.nonNull(dto.getMaxBirthday()), User::getBirthday, dto.getMaxBirthday())
                .notLike(StringUtils.isNotEmpty(dto.getNotLikeEmail()), User::getEmail, dto.getNotLikeEmail())
                .likeLeft(StringUtils.isNotEmpty(dto.getLikeLeftEmail()), User::getEmail, dto.getLikeLeftEmail())
                .likeRight(StringUtils.isNotEmpty(dto.getLikeRightEmail()), User::getEmail, dto.getLikeRightEmail()));
    }

    public List<User> listUsers(UserDto4 dto, boolean includeInheritedFields) {
        boolean setEqId = includeInheritedFields && Objects.nonNull(dto.getId());
        boolean geBirthday = includeInheritedFields && Objects.nonNull(dto.getMinBirthday());
        boolean leBirthday = includeInheritedFields && Objects.nonNull(dto.getMaxBirthday());
        boolean geAge = Objects.nonNull(dto.getAge());

        boolean likeEmail = includeInheritedFields && StringUtils.isNotEmpty(dto.getKeyword());
        boolean likeName = includeInheritedFields && StringUtils.isNotEmpty(dto.getKeyword());
        return list(Wrappers.<User>lambdaQuery()
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

    public List<User> listUsers(UserDto3 dto) {
        boolean setEqId = Objects.nonNull(dto.getId());
        boolean geBirthday = Objects.nonNull(dto.getMinBirthday());
        boolean leBirthday = Objects.nonNull(dto.getMaxBirthday());

        boolean likeEmail = StringUtils.isNotEmpty(dto.getKeyword());
        boolean likeName = StringUtils.isNotEmpty(dto.getKeyword());
        return list(Wrappers.<User>lambdaQuery()
                .and(setEqId || geBirthday || leBirthday, w -> {
                    w.eq(setEqId, User::getId, dto.getId())
                            .ge(geBirthday, User::getBirthday, dto.getMinBirthday())
                            .le(leBirthday, User::getBirthday, dto.getMaxBirthday());
                })
                .and(likeEmail || likeName, w -> {
                    w.like(likeEmail, User::getEmail, dto.getKeyword())
                            .or()
                            .like(likeName, User::getName, dto.getKeyword());
                }));
    }

    public List<User> listUsers(UserDto6 dto, boolean includeInheritedFields) {
        boolean leId = includeInheritedFields && Objects.nonNull(dto.getId());
        boolean likeNameOrEmail = StringUtils.isNotEmpty(dto.getKeyword());
        boolean geBirthday = Objects.nonNull(dto.getDto7()) && Objects.nonNull(dto.getDto7().getBirthday());
        boolean ltAge = Objects.nonNull(dto.getDto7()) && Objects.nonNull(dto.getDto7().getAge());
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery()
                .le(leId, User::getId, dto.getId())
                .and(likeNameOrEmail, w -> {
                    w.like(User::getName, dto.getKeyword())
                            .or()
                            .like(User::getEmail, dto.getKeyword());
                });
        if (geBirthday) {
            wrapper.ge(User::getBirthday, dto.getDto7().getBirthday());
        }
        if (ltAge) {
            wrapper.lt(User::getAge, dto.getDto7().getAge());
        }
        return list(wrapper);
    }

}
