package com.meng.designpatten.singleton;

/**
 * 饿汉式 双检索 多线程安全, volatile 很关键，同时构造函数私有
 * */
public class Singleton02 {

    private volatile static Singleton02 instance = null;

    private Singleton02() {}

    public Singleton02 getInstance() {
        if (instance == null) {
            synchronized (this){
                if (instance == null) {
                    instance = new Singleton02();
                }
            }
        }
        return instance;
    }

}
