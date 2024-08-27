package site.lianwu.dubboprovider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@Slf4j
public class DubboProviderApplication {

     public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
        log.info("--------------------Dubbo Provider 启动成功");
    }

}
