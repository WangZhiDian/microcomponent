package com.meng.designpatten.singleton;

/**
 * 使用饱汉式方式，一开始就创建对象，构造对象私有
 * 多线程安全
 * */
public class Singleton01 {


    private static final Singleton01 instanse = new Singleton01();

    private Singleton01() {

    }

    public Singleton01 getInstanse() {
        return instanse;
    }

}
