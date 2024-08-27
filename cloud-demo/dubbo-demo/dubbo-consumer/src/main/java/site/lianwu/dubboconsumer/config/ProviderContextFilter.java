package site.lianwu.dubboconsumer.config;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;
import site.lianwu.dubbointerface.UserContexts;

@Slf4j
public class ProviderContextFilter implements Filter {

    public ProviderContextFilter() {
        log.info("ProviderContextFilter 过滤器...");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        UserContexts.setUser(JSON.parseObject(invocation.getAttachment("user"), UserContexts.User.class));
        return invoker.invoke(invocation);
    }

}