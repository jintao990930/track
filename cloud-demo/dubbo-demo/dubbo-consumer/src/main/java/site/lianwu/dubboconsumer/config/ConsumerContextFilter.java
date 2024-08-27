package site.lianwu.dubboconsumer.config;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;
import site.lianwu.dubbointerface.UserContexts;

@Slf4j
public class ConsumerContextFilter implements Filter {

    public ConsumerContextFilter() {
        log.info("ConsumerContextFilter 过滤器...");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        UserContexts.User user = new UserContexts.User();
        user.setUserId(3L);
        user.setUsername("lianwu");
        invocation.setAttachment("user", JSON.toJSONString(user));
        return invoker.invoke(invocation);
    }

}
