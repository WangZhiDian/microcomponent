package com.meng.practice.practice.niuke;

import java.util.Scanner;

public class Test {

    // 接收数据示例
    public static void main(String[] args) {
        /*Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }*/

        String input = "AA";
        int ret = Integer.parseInt(input, 16);
        System.out.println(ret);

        double a = Math.sqrt(9);
        System.out.println((int)a);


        String a2 = "abcd32";
        System.out.println(a2.contains("bc3"));


    }
}
