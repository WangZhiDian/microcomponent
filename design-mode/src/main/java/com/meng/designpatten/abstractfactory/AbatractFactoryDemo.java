package com.meng.designpatten.abstractfactory;

/**
 * 抽象工厂模式，解决多类属性问题的集合，每一类就是一个工厂，由抽象工厂类和接口辅助实现
 * */
public class AbatractFactoryDemo {

    public static void main(String[] args) {

        FactoryProducer factoryProducer = new FactoryProducer();
        AbstractFactory colorFactory = factoryProducer.getFactory("color");
        Color color = colorFactory.getColer("red");
        color.fill();
        color = colorFactory.getColer("green");
        color.fill();

        AbstractFactory shapeFactory = factoryProducer.getFactory("shape");
        Shape shape = shapeFactory.getShape("circle");
        shape.draw();
        shape = shapeFactory.getShape("rec");
        shape.draw();

    }
}
