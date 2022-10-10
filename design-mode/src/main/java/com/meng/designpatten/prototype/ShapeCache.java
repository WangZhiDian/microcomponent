package com.meng.designpatten.prototype;

import java.util.HashMap;
import java.util.Map;

public class ShapeCache {

    private static Map<String, Shape> map = new HashMap();

    public Shape getShape(String type) throws CloneNotSupportedException {
        Shape shape = map.get(type);
        Shape ret = (Shape)shape.clone();
        return ret;
    }

    private void load() {
        Circle circle = new Circle();
        Rectangle rec = new Rectangle();
        map.put("circle", circle);
        map.put("rec", rec);
    }

}
