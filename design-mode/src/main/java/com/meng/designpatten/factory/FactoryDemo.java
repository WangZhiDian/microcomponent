package com.meng.designpatten.factory;

public class FactoryDemo {

    public static void main(String[] args) {

        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape1 = shapeFactory.getShape("circle");
        shape1.draw();
        shape1 = shapeFactory.getShape("rec");
        shape1.draw();
    }

}
