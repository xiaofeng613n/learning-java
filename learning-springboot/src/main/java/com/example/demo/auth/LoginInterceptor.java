package com.example.demo.auth;

import com.example.demo.auth.LoginManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by xiaofeng on 2018/11/5
 * Description:
 */
public class LoginInterceptor implements HandlerInterceptor {

    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        Cookie[] cookies = httpServletRequest.getCookies();
        if( cookies != null ){
            for (Cookie cookie : cookies) {
                if( cookie.getName().equals("loginId") ) {
                    String loginId = cookie.getValue();
                    if( LoginManager.check(loginId)){
                        return true;
                    }
                }
            }
        }
        //httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/toLoginPage");  //未登录自动跳转界面
//        httpServletResponse.setStatus(403);
        return false;
    }

//    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
//        httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST");
//        httpServletResponse.addHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
//        System.out.println("postHandle被调用\n");
//    }
//
////    在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("afterCompletion被调用\n");
//        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
//        httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST");
//        httpServletResponse.addHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
//    }
}