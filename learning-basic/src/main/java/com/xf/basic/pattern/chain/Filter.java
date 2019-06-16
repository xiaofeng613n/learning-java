package com.xf.basic.pattern.chain;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public interface Filter {

    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain);
}
