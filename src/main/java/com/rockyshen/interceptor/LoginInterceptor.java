package com.rockyshen.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

/**
 * 登录拦截器
 * @author rockyshen
 * @date 2026/1/8 11:15
 */

@Configuration
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return false;
        }

        String userCookie = Arrays.stream(cookies).filter(e -> Objects.equals("user_cookie", e.getName())).findFirst().map(Cookie::getValue).orElse(null);

        if (Objects.equals(userCookie, "hello")){
            return true;
        }else{
            return false;
        }


    }
}
