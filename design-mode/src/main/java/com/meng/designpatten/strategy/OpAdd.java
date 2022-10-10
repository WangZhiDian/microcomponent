package com.meng.designpatten.strategy;

public class OpAdd implements Strategy {
    @Override
    public void operation(int a, int b) {
        System.out.println(" a + b = " + (a + b));
    }
}
