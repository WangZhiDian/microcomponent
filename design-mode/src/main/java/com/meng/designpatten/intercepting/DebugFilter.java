package com.meng.designpatten.intercepting;

public class DebugFilter implements Filter {
    @Override
    public void doFilter() {
        System.out.println(" i am debug filter");
    }
}
