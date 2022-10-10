package com.meng.designpatten.abstractfactory;

public class ColorFactory extends AbstractFactory {


    @Override
    public Color getColer(String type) {
        if ("red".equals(type)) {
            return new Red();
        } else if ("green".equals(type)) {
            return new Green();
        } else {
            return null;
        }
    }

    @Override
    public Shape getShape(String type) {
        return null;
    }
}
