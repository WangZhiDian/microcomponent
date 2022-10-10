package com.meng.designpatten.proxy;

public class CircleProxy implements Image{

    Circle circle;


    @Override
    public void draw() {
        if (circle == null) {
            circle = new Circle();
        }
        circle.draw();
        System.out.println(" i am in proxy");
    }
}
