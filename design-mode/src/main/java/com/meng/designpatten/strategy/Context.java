package com.meng.designpatten.strategy;

public class Context {

    Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void exec(int a, int b) {
        strategy.operation(a, b);
    }

}
