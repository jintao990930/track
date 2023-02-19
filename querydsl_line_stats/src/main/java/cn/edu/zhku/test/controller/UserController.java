package cn.edu.zhku.test.controller;

import cn.edu.zhku.dto.BaseDataStatsDTO;
import cn.edu.zhku.test.dto.UserDTO;
import cn.edu.zhku.test.entity.User;
import cn.edu.zhku.test.service.UserService;
import cn.edu.zhku.test.vo.AvgHeightVO;
import cn.edu.zhku.test.vo.AvgWeightVO;
import cn.edu.zhku.vo.BaseDataStatsVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping("save")
    public void saveUser(@RequestBody @Validated UserDTO dto) {
        userService.save(dto);
    }

    @PostMapping("stats/avg/height")
    public List<BaseDataStatsVO<Double>> avgHeightStats(@RequestBody BaseDataStatsDTO dto) {
        return userService.avgHeightStats(dto);
    }

    @PostMapping("stats/avg/weight")
    public List<BaseDataStatsVO<Double>> avgWeightStats(@RequestBody BaseDataStatsDTO dto) {
        return userService.avgWeightStats(dto);
    }

    @PostMapping("stats/avg/heightGroupByGender")
    public List<BaseDataStatsVO<AvgHeightVO>> avgHeightGroupByGenderStats(@RequestBody BaseDataStatsDTO dto) {
        return userService.avgHeightGroupByGenderStats(dto);
    }

    @PostMapping("stats/avg/weightGroupByGender")
    public List<BaseDataStatsVO<AvgWeightVO>> avgWeightGroupByGenderStats(@RequestBody BaseDataStatsDTO dto) {
        return userService.avgWeightGroupByGenderStats(dto);
    }

}
