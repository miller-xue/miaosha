package com.miller.seckill;

import com.google.common.collect.Maps;
import com.miller.seckill.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by miller on 2018/8/6
 */
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    /*@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        LoginFilter loginFilter = new LoginFilter();

        registrationBean.setFilter(loginFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);

        Map<String, String> params = Maps.newHashMap();
        params.put("targetFilterLifecycle", "true");
        registrationBean.setInitParameters(params);

        return registrationBean;
    }*/
}
