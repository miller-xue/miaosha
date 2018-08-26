package com.miller.seckill.config;

import com.miller.seckill.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by miller on 2018/8/11
 * webMvc 参数适配器
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UserArgumentResolvers userArgumentResolvers;

    @Autowired
    private AccessInterceptor accessInterceptor;

    /**
     * 参数 ArgumentResolvers
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
    }
}
