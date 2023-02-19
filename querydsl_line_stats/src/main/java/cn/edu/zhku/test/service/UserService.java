package cn.edu.zhku.test.service;

import cn.edu.zhku.bo.LineStatsQuery;
import cn.edu.zhku.dto.BaseDataStatsDTO;
import cn.edu.zhku.service.CommLineStatsService;
import cn.edu.zhku.test.dto.UserDTO;
import cn.edu.zhku.test.entity.QUser;
import cn.edu.zhku.test.entity.User;
import cn.edu.zhku.test.repository.RUser;
import cn.edu.zhku.test.vo.AvgHeightVO;
import cn.edu.zhku.test.vo.AvgWeightVO;
import cn.edu.zhku.vo.BaseDataStatsVO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private RUser rUser;
    @Resource
    private CommLineStatsService commLineStatsService;

    private final QUser qUser = QUser.user;

    public User findById(Integer id) {
        return rUser.findById(id).orElse(null);
    }

    @Transactional
    public void save(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        rUser.save(user);
    }

    public List<BaseDataStatsVO<Double>> avgHeightStats(BaseDataStatsDTO dto) {
        LineStatsQuery<Double> query = LineStatsQuery.<Double>builder()
                .select(qUser.height.avg())
                .from(qUser)
                .datePath(qUser.birthday)
                .build();
        return commLineStatsService.getStatsList(dto, query, 0d);
    }

    public List<BaseDataStatsVO<Double>> avgWeightStats(BaseDataStatsDTO dto) {
        LineStatsQuery<Double> query = LineStatsQuery.<Double>builder()
                .select(qUser.weight.avg())
                .from(qUser)
                .datePath(qUser.birthday)
                .build();
        return commLineStatsService.getStatsList(dto, query, 0d);
    }

    public List<BaseDataStatsVO<AvgHeightVO>> avgHeightGroupByGenderStats(BaseDataStatsDTO dto) {
        LineStatsQuery<Tuple> query = LineStatsQuery.<Tuple>builder()
                .select(Projections.tuple(qUser.gender,
                        qUser.height.avg().as("avgHeight")))
                .from(qUser)
                .datePath(qUser.birthday)
                .groupBy(qUser.gender)
                .build();
        return commLineStatsService.getStatsList(dto, query, new AvgHeightVO(), list -> {
            AvgHeightVO res = new AvgHeightVO();
            for (Tuple tuple : list) {
                if ("female".equals(tuple.get(qUser.gender))) {
                    res.setFemaleAvgHeight(tuple.get(1, Double.class));
                } else if ("male".equals(tuple.get(qUser.gender))) {
                    res.setMaleAvgHeight(tuple.get(1, Double.class));
                }
            }
            return res;
        });
    }

    public List<BaseDataStatsVO<AvgWeightVO>> avgWeightGroupByGenderStats(BaseDataStatsDTO dto) {
        LineStatsQuery<Tuple> query = LineStatsQuery.<Tuple>builder()
                .select(Projections.tuple(qUser.gender,
                        qUser.weight.avg().as("avgWeight")))
                .from(qUser)
                .datePath(qUser.birthday)
                .groupBy(qUser.gender)
                .build();
        return commLineStatsService.getStatsList(dto, query, new AvgWeightVO(), list -> {
            AvgWeightVO res = new AvgWeightVO();
            for (Tuple tuple : list) {
                if ("female".equals(tuple.get(qUser.gender))) {
                    res.setFemaleAvgWeight(tuple.get(1, Double.class));
                } else if ("male".equals(tuple.get(qUser.gender))) {
                    res.setMaleAvgWeight(tuple.get(1, Double.class));
                }
            }
            return res;
        });
    }

}
