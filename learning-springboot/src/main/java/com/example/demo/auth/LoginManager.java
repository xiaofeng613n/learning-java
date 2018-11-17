package com.example.demo.auth;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaofeng on 2018/11/5
 * Description:
 */
public class LoginManager {

    private static LRUMap<String,Object> loginIdMap = new LRUMap<>(500);

    private static Map<String,String> usermap = new HashMap<>();

    public static void register(String username, String password){
        usermap.put(username, password);
    }

    public static void logout(String loginId) {
        loginIdMap.remove(loginId);
    }


    public static boolean check(String loginId) {
        Object o = loginIdMap.get(loginId);
        if (o != null) {
            return true;
        }
        return false;
    }

    public static String login(String username, String password) {

        String rightPassword = usermap.get(username);
        if ( rightPassword == null || Strings.isBlank(rightPassword)) {
            return null;
        }

        if ( rightPassword.equals(password)) {
            String loginId = DigestUtils.md5DigestAsHex(rightPassword.getBytes());
            loginIdMap.put(loginId, new Object());
            return loginId;
        }
        return null;
    }
}