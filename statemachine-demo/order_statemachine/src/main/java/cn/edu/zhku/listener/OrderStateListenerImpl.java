package cn.edu.zhku.listener;

import cn.edu.zhku.consts.StateMachineConsts;
import cn.edu.zhku.entity.Order;
import cn.edu.zhku.enums.OrderStatus;
import cn.edu.zhku.enums.OrderStatusChangeEvent;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component
@WithStateMachine(name = StateMachineConsts.ORDER_STATEMACHINE_NAME)
public class OrderStateListenerImpl {

    @OnTransition(source = StateMachineConsts.ORDER_STATUS_WAIT_PAYMENT, target = StateMachineConsts.ORDER_STATUS_WAIT_DELIVER)
    public boolean paymentTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get(StateMachineConsts.ORDER_MESSAGE_NAME);
        order.setStatus(OrderStatus.WAIT_DELIVER);
        System.out.println("支付，状态机反馈信息：" + message.getHeaders());
        return true;
    }

    @OnTransition(source = StateMachineConsts.ORDER_STATUS_WAIT_DELIVER, target = StateMachineConsts.ORDER_STATUS_WAIT_RECEIVE)
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get(StateMachineConsts.ORDER_MESSAGE_NAME);
        order.setStatus(OrderStatus.WAIT_RECEIVE);
        System.out.println("发货，状态机反馈信息：" + message.getHeaders());
        return true;
    }

    @OnTransition(source = StateMachineConsts.ORDER_STATUS_WAIT_RECEIVE, target = StateMachineConsts.ORDER_STATUS_FINISH)
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get(StateMachineConsts.ORDER_MESSAGE_NAME);
        order.setStatus(OrderStatus.FINISH);
        System.out.println("签收，状态机反馈信息：" + message.getHeaders());
        return true;
    }


}
