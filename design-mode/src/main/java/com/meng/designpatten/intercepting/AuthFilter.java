package com.meng.designpatten.intercepting;

public class AuthFilter implements Filter {
    @Override
    public void doFilter() {
        System.out.println(" i am auth filter");
    }
}
