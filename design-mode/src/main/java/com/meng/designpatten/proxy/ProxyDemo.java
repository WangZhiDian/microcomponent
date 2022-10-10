package com.meng.designpatten.proxy;

public class ProxyDemo {

    public static void main(String[] args) {


        Image image = new CircleProxy();
        image.draw();
    }
}
