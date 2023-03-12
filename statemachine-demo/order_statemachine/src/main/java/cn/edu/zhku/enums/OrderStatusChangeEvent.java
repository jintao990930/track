package cn.edu.zhku.enums;

import lombok.Getter;

/**
 * 订单状态改变事件
 */
@Getter
public enum OrderStatusChangeEvent {

    PAYED("PAYED", "支付"),
    DELIVERY("DELIVERY", "发货"),
    RECEIVED("RECEIVED", "签收");

    final String name;
    final String desc;

    OrderStatusChangeEvent(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

}
