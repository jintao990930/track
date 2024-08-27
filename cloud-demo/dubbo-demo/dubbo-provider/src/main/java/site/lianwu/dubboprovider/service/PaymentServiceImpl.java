package site.lianwu.dubboprovider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import site.lianwu.dubbointerface.IPaymentService;
import site.lianwu.dubbointerface.UserContexts;

/**
 * timeout：(保护机制)。Dubbo利用超时机制，当无法在指定时间内完成服务访问，则自动断开连接。（服务器提供者和消费者都可以配置超时时间）
 * version: 多版本，用于灰度发布（金丝雀发布）
 */
@DubboService(timeout = 2500/*, version = "2.0.0" */)
@Slf4j
public class PaymentServiceImpl implements IPaymentService {

    @Override
    public String pay(String id) {
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        UserContexts.User user = UserContexts.getUser();
        return "user: " + user + ", paid " + id + " successfully!";
    }

}
