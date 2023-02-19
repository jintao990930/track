package cn.edu.zhku.config;

import cn.edu.zhku.converter.BaseDataStatsDTOConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Resource
    private BaseDataStatsDTOConverter baseDataStatsDTOConverter;

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, baseDataStatsDTOConverter);
    }

}
