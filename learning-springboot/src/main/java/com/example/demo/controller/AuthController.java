package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.auth.LoginManager;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaofeng on 2018/11/5
 * Description:
 */
@RestController
public class AuthController {

    @Value("${username1}")
    private String username;

    @Value("${password1}")
    private String password;

    @PostConstruct
    public void initUserMap(){
        LoginManager.register(username,password);
    }

    @RequestMapping("/login")
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (null == username || null == password) {
            response.setStatus(403);
            return getResponse("empty username or password");
        }

        String loginId = LoginManager.login(username,password);

        if ( loginId == null || Strings.isBlank(loginId)) {
            response.setStatus(403);
            return getResponse("error username or password");
        }

        Cookie cookie = new Cookie("loginId",loginId);
        cookie.setMaxAge( 3600 * 24);
        response.addCookie(cookie);
        JSONObject object = getResponse("OK");
        object.put("loginId",loginId);
        return object;
    }

    private JSONObject getResponse(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        return jsonObject;
    }

    @RequestMapping("/logout")
    public JSONObject logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if( cookie.getName().equals("loginId")) {

                String loginId = cookie.getValue();
                LoginManager.logout(loginId);

                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return getResponse("logout");
    }

    @RequestMapping("/check")
    public JSONObject check( HttpServletRequest request, HttpServletResponse response ) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if( cookie.getName().equals("loginId") ) {
                    String loginId = cookie.getValue();
                    if( LoginManager.check(loginId)){
                        return getResponse("YES");
                    }
                }
            }
        }
        return getResponse("NO");
    }

}