package com.meng.designpatten.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<BaseColor> {

    @Override
    public BaseColor getObject() {
        BaseColor color = new BaseColor();
        color.color = "red";
        return color;
    }

    @Override
    public Class<?> getObjectType() {
        return BaseColor.class;
    }
}
