package com.rockyshen.config;

import com.rockyshen.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author rockyshen
 * @date 2026/1/8 11:43
 */
@Component
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    public LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                /**
                 * 排除登录接口
                 * 1、用户携带userName + password，发起登录。登录时自动种下cookie
                 * 2、其他资源处于拦截之下，请求时带上cookie，成功的话，通过拦截器
                 */
                .excludePathPatterns("/user/login");
    }
}
