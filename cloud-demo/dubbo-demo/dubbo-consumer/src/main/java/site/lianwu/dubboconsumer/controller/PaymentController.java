package site.lianwu.dubboconsumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.lianwu.dubbointerface.IPaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    /**
     * check:启动时检查，服务提供者宕机了也能启动成功
     * 地址缓存：服务消费者在第一次调用时，会将服务提供者的地址缓存到本地，以后再调用时不会再访问注册中心。
     *         服务提供者地址发生变化后，注册中心会通知服务消费者
     * timeout：(保护机制)。Dubbo利用超时机制，当无法在指定时间内完成服务访问，则自动断开连接。
     * retries：服务调用失败后重试次数。
     * version: 多版本，用于灰度发布（金丝雀发布）
     * loadbalance: 负载均衡策略
     * cluster: 集群容错策略
     *
     */
    @DubboReference(check = false, timeout = 1000, retries = 3/*, loadbalance = LoadbalanceRules.RANDOM, cluster = ClusterRules.FAIL_FAST*/)
    private IPaymentService paymentService;

    @GetMapping("/pay-logs/{id}")
    public String pay(@PathVariable(name = "id") String id) {
        return paymentService.pay(id);
    }

}
