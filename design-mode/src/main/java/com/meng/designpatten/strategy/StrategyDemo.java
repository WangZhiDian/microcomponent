package com.meng.designpatten.strategy;

/**
* 将策略作为参数传递，使用接口的方式实现
* */
public class StrategyDemo {

    public static void main(String[] args) {
        Context context = new Context(new OpAdd());
        context.exec(1, 3);
        context = new Context(new OpSub());
        context.exec(3, 1);
    }
}
