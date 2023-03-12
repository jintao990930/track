package cn.edu.zhku.enums;

import lombok.Getter;

/**
 * 订单状态
 */
@Getter
public enum OrderStatus {

    WAIT_PAYMENT("WAIT_PAYMENT", "待支付"),
    WAIT_DELIVER("WAIT_DELIVER", "待发货"),
    WAIT_RECEIVE("WAIT_RECEIVE", "待签收"),
    FINISH("FINISH", "完成");

    final String name;
    final String desc;

    OrderStatus(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

}
