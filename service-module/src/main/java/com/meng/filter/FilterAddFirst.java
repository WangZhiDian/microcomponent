package com.meng.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(2)
public class FilterAddFirst implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("init in filter FilterAddFirst--");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("doFilter in FilterAddFirst ----");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("doFilter in FilterAddFirst after ----");

    }
}
