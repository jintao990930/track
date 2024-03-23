package cn.lianwu.config;

import cn.lianwu.mybatis.plus.ext.query.QueryWrapperTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public QueryWrapperTemplate queryWrapperParser() {
        return new QueryWrapperTemplate();
    }

}
