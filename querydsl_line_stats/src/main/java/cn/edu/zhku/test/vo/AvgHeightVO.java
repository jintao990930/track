package cn.edu.zhku.test.vo;

import lombok.Data;

@Data
public class AvgHeightVO {

    private Double femaleAvgHeight;

    private Double maleAvgHeight;

    public AvgHeightVO() {
        femaleAvgHeight = 0d;
        maleAvgHeight = 0d;
    }

}
