package com.ryanbing.config;

import com.ryanbing.config.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ryan
 **/

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    /**
     * 权限拦截器注册
     */
    @Bean
    public AuthorizationInterceptor authenticationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 默认返回类型
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
