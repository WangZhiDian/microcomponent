package com.meng.designpatten.bridge;

public class BridgeDemo {

    public static void main(String[] args) {


        Circle circle = new Circle(new RedColor());
        circle.draw();

        circle = new Circle(new GreenColor());
        circle.draw();

    }
}
