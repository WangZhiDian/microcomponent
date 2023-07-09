package com.meng.filter;

import javax.servlet.*;
import java.io.IOException;

public class CustormFilter01 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("customer 1");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("customer 1 after");
    }
}
