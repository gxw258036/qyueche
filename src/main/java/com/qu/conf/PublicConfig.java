package com.qu.conf;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Created by guxiaowei on 2017/9/30.
 */
@Configuration
public class PublicConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String API_NAME = "/api/";

    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(
            @Value("classpath*:mappings/*mappings.xml") Resource[] resources) throws Exception {
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        // Other configurations
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
    }
}
