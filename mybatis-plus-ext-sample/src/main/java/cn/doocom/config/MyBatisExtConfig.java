package cn.doocom.config;

import cn.doocom.mybatis.plus.ext.query.QueryWrapperTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisExtConfig {

    @Bean
    public QueryWrapperTemplate queryWrapperParser() {
        return new QueryWrapperTemplate();
    }

}
