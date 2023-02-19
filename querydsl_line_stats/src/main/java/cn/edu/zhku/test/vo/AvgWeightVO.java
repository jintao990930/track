package cn.edu.zhku.test.vo;

import lombok.Data;

@Data
public class AvgWeightVO {

    private Double femaleAvgWeight;

    private Double maleAvgWeight;

    public AvgWeightVO() {
        femaleAvgWeight = 0d;
        maleAvgWeight = 0d;
    }

}
