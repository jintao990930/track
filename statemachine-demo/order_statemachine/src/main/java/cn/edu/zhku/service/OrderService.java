package cn.edu.zhku.service;

import cn.edu.zhku.entity.Order;

import java.util.Map;

public interface OrderService {

    // 创建订单
    Order create();

    // 发起支付
    Order pay(int id);

    // 订单发货
    Order deliver(int id);

    // 订单签收
    Order receive(int id);

    Map<Integer, Order> mapOrders();

}
