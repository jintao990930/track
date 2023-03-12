package cn.edu.zhku.entity;

import cn.edu.zhku.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Order {

    private int id;
    private OrderStatus status;

}
