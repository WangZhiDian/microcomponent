package com.meng.designpatten.composite;

public class CompositeDemo {

    public static void main(String[] args) {


        Employ boss = new Employ("boss", 100000);
        Employ market = new Employ("market", 1000);
        Employ market2 = new Employ("market", 1000);

        boss.list.add(market);
        boss.list.add(market);

        System.out.println("print all employ");
    }
}
