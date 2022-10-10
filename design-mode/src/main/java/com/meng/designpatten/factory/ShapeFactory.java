package com.meng.designpatten.factory;

public class ShapeFactory {

    public Shape getShape(String type) {
        if ("circle".equals(type)) {
            return new Circle();
        } else if ("rec".equals(type)) {
            return new rectangle();
        } else {
            return null;
        }
    }

}
