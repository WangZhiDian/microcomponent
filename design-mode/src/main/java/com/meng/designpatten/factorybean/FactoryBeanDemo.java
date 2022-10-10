package com.meng.designpatten.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanDemo {

    public static void main(String[] args) {
        FactoryBean factoryBean = new ColorFactoryBean();
        BaseColor color = ((ColorFactoryBean) factoryBean).getObject();
        System.out.println(color.color);
    }
}
