package com.meng.designpatten.decorate;

public abstract class DecorateShape {

    Shape shape;

    public DecorateShape(Shape shape) {
        this.shape = shape;
    }

    public void draw() {
        shape.draw();
    }
}
