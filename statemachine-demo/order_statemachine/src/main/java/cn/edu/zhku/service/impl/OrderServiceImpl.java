package cn.edu.zhku.service.impl;

import cn.edu.zhku.consts.StateMachineConsts;
import cn.edu.zhku.entity.Order;
import cn.edu.zhku.enums.OrderStatus;
import cn.edu.zhku.enums.OrderStatusChangeEvent;
import cn.edu.zhku.service.OrderService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StateMachine<OrderStatus, OrderStatusChangeEvent> stateMachine;

    @Resource
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

    private int id = 1;
    private Map<Integer, Order> orderMap = new HashMap<>();


    @Override
    public Order create() {
        Order order = new Order();
        order.setStatus(OrderStatus.WAIT_PAYMENT);
        order.setId(id++);
        orderMap.put(order.getId(), order);
        return order;
    }

    @Override
    public Order pay(int id) {
        Order order = orderMap.get(id);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试支付，订单号：" + id);
        Message<OrderStatusChangeEvent> message = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED)
                .setHeader(StateMachineConsts.ORDER_MESSAGE_NAME, order)
                .build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 支付失败，状态异常，订单号：" + id);
        }
        return orderMap.get(id);
    }

    @Override
    public Order deliver(int id) {
        Order order = orderMap.get(id);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试发货，订单号：" + id);
        Message<OrderStatusChangeEvent> message = MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY)
                .setHeader(StateMachineConsts.ORDER_MESSAGE_NAME, order)
                .build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 发货失败，状态异常，订单号：" + id);
        }
        return orderMap.get(id);
    }

    @Override
    public Order receive(int id) {
        Order order = orderMap.get(id);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试签收，订单号：" + id);
        Message<OrderStatusChangeEvent> message = MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED)
                .setHeader(StateMachineConsts.ORDER_MESSAGE_NAME, order)
                .build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 签收失败，状态异常，订单号：" + id);
        }
        return orderMap.get(id);
    }

    @Override
    public Map<Integer, Order> mapOrders() {
        return orderMap;
    }

    private synchronized boolean sendEvent(Message<OrderStatusChangeEvent> message, Order order) {
        boolean result = false;
        try {
            stateMachine.start();
            // 尝试恢复状态机状态
            persister.restore(stateMachine, order);
            // 添加延迟用于线程安全测试
            Thread.sleep(1000);
            result = stateMachine.sendEvent(message);
            // 持久化状态机状态
            persister.persist(stateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stateMachine.stop();
        }
        return result;
    }

}
