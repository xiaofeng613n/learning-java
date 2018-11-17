package com.example.demo.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaofeng on 2018/11/5
 * Description:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPattern后跟拦截地址，excludePathPatterns后跟排除拦截地址
//        registry.addInterceptor(new HandlerInterceptor() {
//            @Override
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//                response.addHeader("Access-Control-Allow-Origin","*");
//                response.addHeader("Access-Control-Allow-Methods", "POST,GET");
//                response.addHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
//                return true;
//            }
//        }).addPathPatterns("/**");

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/check")
                .excludePathPatterns("/*.js")
                .excludePathPatterns("/*.css")
                .excludePathPatterns("/*.html")
                .excludePathPatterns("/*.null")
                .excludePathPatterns("/*.jpg");


    }
}