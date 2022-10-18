package com.meng.practice.practice.valid.extendValid;


public class Son extends Father {

    @Override
    public int sayHello() {
        System.out.println(" I am Son");
        return super.sayHello();
    }
}
