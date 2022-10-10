package com.meng.designpatten.decorate;

public class DecorateDemo {

    public static void main(String[] args) {

        RedDecorateShape decorate = new RedDecorateShape(new Circle());
        decorate.draw();

        decorate = new RedDecorateShape(new Rectangle());
        decorate.draw();
    }
}
