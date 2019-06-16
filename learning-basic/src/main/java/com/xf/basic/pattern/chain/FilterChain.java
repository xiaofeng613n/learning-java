package com.xf.basic.pattern.chain;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xiaofeng
 * @Date: 2019-06-10 20:10
 * @Description:
 */
public class FilterChain implements Filter {

    private List<Filter> filters;
    private int index = 0;

    public FilterChain() {
        filters = new ArrayList<>();
    }

    public void add(Filter filter) {
        filters.add(filter);
    }

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) {
        if ( index >= filters.size() ) {
            return;
        }
        Filter filter = filters.get(index ++);
        filter.doFilter(request, response, chain);
    }


    public static void main(String[] args) {

        FilterChain filterChain = new FilterChain();
        filterChain.add(new LogFilter());

        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        filterChain.doFilter(httpRequest, httpResponse, filterChain);
        //过滤器栈

    }
}
