package com.meng.designpatten.abstractfactory;

public class ShapeFactory extends AbstractFactory{

    @Override
    public Color getColer(String type) {
        return null;
    }

    @Override
    public Shape getShape(String type) {
        if ("circle".equals(type)) {
            return new Circle();
        } else if ("rec".equals(type)) {
            return new Rectangle();
        } else {
            return null;
        }
    }

}
