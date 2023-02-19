package cn.edu.zhku.enmu;

import lombok.Getter;

@Getter
public enum StatsTimeUnit {

    /**
     * 年
     */
    Y("年"),

    /**
     * 月
     */
    M("月"),

    /**
     * 日
     */
    D("日"),

    /**
     * 时
     */
    H("时"),

    /**
     * 24时
     */
    H_24("24时"),
    ;

    private final String text;

    StatsTimeUnit(String text) {
        this.text = text;
    }

}
