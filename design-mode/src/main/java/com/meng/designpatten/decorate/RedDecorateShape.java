package com.meng.designpatten.decorate;

public class RedDecorateShape extends DecorateShape{

    public RedDecorateShape(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        shape.draw();
        extermFunc();
    }

    private void extermFunc() {
        System.out.println(" i am a extern func");
    }
}
