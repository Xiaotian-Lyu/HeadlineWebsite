package com.atguigu.config;

import com.atguigu.interceptors.LoginProtectedInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    //WebMvcConfigurer 是 Spring MVC 中的一个接口，
    // 用于配置和定制化 Spring MVC 的行为。通过实现该接口，
    // 你可以覆盖其中的方法，以自定义一些 MVC 特性，如拦截器、资源处理、消息转换器等。
    @Autowired
    private LoginProtectedInterceptor loginProtectedInterceptor;

    /**
     * 是 Spring MVC 中 WebMvcConfigurer 接口的一个方法，用于配置拦截器（Interceptors）。
     * 通过实现 WebMvcConfigurer 接口，你可以覆盖 addInterceptors 方法，并在其中添加自定义的拦截器。
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginProtectedInterceptor).addPathPatterns("/headline/**");
    }
}
