package com.meng.designpatten.bridge;

public class Circle extends Shape {
    public Circle(DrawApi drawApi) {
        super(drawApi);
    }

    @Override
    public void draw() {
        drawApi.draw();
    }
}
