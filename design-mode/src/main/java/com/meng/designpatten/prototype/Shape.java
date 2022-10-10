package com.meng.designpatten.prototype;

import lombok.Data;

@Data
public abstract class Shape implements Cloneable{

    private String type;

    public abstract void draw();

    public Object clone() throws CloneNotSupportedException {
            Object clone = super.clone();
            return clone;
    }


}
