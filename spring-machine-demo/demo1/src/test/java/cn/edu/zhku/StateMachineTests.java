package cn.edu.zhku;

import cn.edu.zhku.enums.OrderEvents;
import cn.edu.zhku.enums.OrderStates;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

@SpringBootTest
public class StateMachineTests {

    @Autowired
    private StateMachine<OrderStates, OrderEvents> orderStateMachine;

    @Test
    public void test() {
        // 创建流程
        orderStateMachine.start();

        // 触发PAY事件
        orderStateMachine.sendEvent(OrderEvents.PAY);

        // 触发RECEIVE事件
        orderStateMachine.sendEvent(OrderEvents.RECEIVE);

        // 获取最终状态
        System.out.println("最终状态：" + orderStateMachine.getState().getId());
    }

}
