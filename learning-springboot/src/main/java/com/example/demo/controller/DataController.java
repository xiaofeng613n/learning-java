package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.auth.LoginManager;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by xiaofeng on 2018/11/5
 * Description:
 */

@RestController
public class DataController  {

    private static Logger logger = LoggerFactory.getLogger(DataController.class);

    private JedisPool jedisPool;

    @Value("${timeZone}")
    private String timeZone;

    @Value("${redis.ip}")
    private String ip;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.pwd}")
    private String pwd;

    private List<String> webList = Arrays.asList("rosegalweb","gamissweb","zafulweb","dresslilyweb","gb-soa-production","rosewholesaleweb");

    @PostConstruct
    private void initRedis(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(400);
        jedisPoolConfig.setMinIdle(80);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);

        logger.info("init JedistPool {}, {}, {}", ip, pwd, port);
        logger.info("timeZone {}", timeZone);

        jedisPool = new JedisPool(jedisPoolConfig,ip,port,5000,pwd);
    }

    @RequestMapping("/rosegal")
    public JSONObject rosegal(){
        return search("rosegalweb");
    }

    @RequestMapping("/gamiss")
    public JSONObject gamiss(){
        return search("gamissweb");
    }

    @RequestMapping("/zaful")
    public JSONObject zaful(){
        return search("zafulweb");
    }

    @RequestMapping("/dresslily")
    public JSONObject dresslily(){
        return search("dresslilyweb");
    }

    @RequestMapping("/gearbest")
    public JSONObject gearbest(){
        return search("gb-soa-production");
    }

    @RequestMapping("/rosewholesale")
    public JSONObject rosewholesale(){
        return search("rosewholesaleweb");
    }

    @RequestMapping("/all")
    public JSONObject all(){
        return searchAll();
    }

    private JSONObject searchAll() {
        try ( Jedis jedis = jedisPool.getResource()) {
            long startTimeOfDay = getStartTimeOfDay(System.currentTimeMillis(), timeZone);
            Map<String, Double> total = new HashMap<>();
            total.put("userAddShoppingCart",Double.valueOf(0));
            total.put("userCreateOrder",Double.valueOf(0));
            total.put("userPay",Double.valueOf(0));
            total.put("orderAmount",Double.valueOf(0));
            for (String key : webList) {
                key = "realtime__" + key + "__" + startTimeOfDay;
                Map<String,String> data = jedis.hgetAll(key);
                if ( data != null && data.size() == 4) {
                    total.put("userAddShoppingCart", total.get("userAddShoppingCart") + Double.valueOf(data.get("userAddShoppingCart")));
                    total.put("userCreateOrder", total.get("userCreateOrder") + Double.valueOf(data.get("userCreateOrder")));
                    total.put("userPay", total.get("userPay") + Double.valueOf(data.get("userPay")));
                    total.put("orderAmount", total.get("orderAmount") + Double.valueOf(data.get("orderAmount")));
                }
            }
            return  JSONObject.parseObject(JSON.toJSONString(total));
        } catch (Exception e ) {
            logger.error("{}", e);
        }
        return getDefaultResponse();
    }

    private JSONObject search(String key) {

        try ( Jedis jedis = jedisPool.getResource()) {
            key = "realtime__" + key + "__" + getStartTimeOfDay(System.currentTimeMillis(), timeZone);
            Map<String,String> data = jedis.hgetAll(key);
            return JSONObject.parseObject(JSON.toJSONString(data));
        } catch (Exception e ) {
            logger.error("{}", e);
        }
        return getDefaultResponse();
    }

    //获取当天（按当前传入的时区）00:00:00所对应时刻的long型值
    private long getStartTimeOfDay(long now, String timeZone) {
//        String tz = Strings. ? "GMT+8" : timeZone;
        TimeZone curTimeZone = TimeZone.getTimeZone(timeZone);
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private JSONObject getDefaultResponse() {
        JSONObject response = new JSONObject();
        response.put("msg","no data found");
        return response;
    }

}