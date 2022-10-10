package com.meng.designpatten.bridge;

public abstract class Shape {

    DrawApi drawApi;

    public Shape(DrawApi drawApi) {
        this.drawApi = drawApi;
    }

    public void draw(){};

}
