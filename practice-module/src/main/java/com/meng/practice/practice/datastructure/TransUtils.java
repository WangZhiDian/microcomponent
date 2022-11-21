package com.meng.practice.practice.datastructure;

public class TransUtils {

    private void Jinzhi() {
        /*
        Integer.toBinaryString(i)表示十进制转为二进制
        Integer.toOctalString(i)表示十进制转为八进制
        Integer.toHexString(i)表示十进制转为十六进制
        Integer.toString(num, 3) 转3进制
        */
        // 十进制转其他进制
        int num = 10;
        System.out.println(Integer.toBinaryString(num));
        System.out.println(Integer.toOctalString(num));
        System.out.println(Integer.toHexString(num));
        System.out.println(Integer.toString(num, 3));

        /*
        * Integer.parseInt((String) s,(int) a); a进制的字符串s转为十进制
        (返回Integer类型）
        Integer.valueOf(String s, int radix)radix进制的字符串s转为十进制
        (返回int类型)
        */

        // N 进制 -> 十进制
        String str = "21104";
        int N = 5;
        System.out.println(str + " 的十进制是:" + Integer.parseInt(str, N));

    }


    public static void main(String[] args) {

        new TransUtils().Jinzhi();


    }


}
