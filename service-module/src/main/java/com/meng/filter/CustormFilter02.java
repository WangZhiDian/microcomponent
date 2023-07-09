package com.meng.filter;


import javax.servlet.*;
import java.io.IOException;

public class CustormFilter02 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("custom 02");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("custom 02 after");
    }
}
