package com.meng.practice.practice.niuke;

public class RandomTest {

    private void test() {

        int num = 897;
        int num2 = num & 11111111;

        System.out.println(num2);

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        boolean b = num2 > 2147483647 + 1 ;
        System.out.println(b);


        System.out.println("23".compareTo("123"));

    }

    public static void main(String[] args) {
        new RandomTest().test();
    }

}
