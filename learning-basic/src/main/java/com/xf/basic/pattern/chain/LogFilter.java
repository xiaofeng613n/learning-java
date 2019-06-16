package com.xf.basic.pattern.chain;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @Auther: xiaofeng
 * @Date: 2019-06-10 20:20
 * @Description:
 */
public class LogFilter implements Filter {

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) {


        chain.doFilter(request, response, chain);
    }
}
