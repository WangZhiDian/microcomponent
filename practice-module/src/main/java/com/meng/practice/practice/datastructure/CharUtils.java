package com.meng.practice.practice.datastructure;

public class CharUtils {

    private void CharaterUtil() {

        String str = "234abcABC!";
        boolean num = Character.isDigit('2');
        System.out.println("2 是数字：" + Character.isDigit('2'));
        System.out.println("a 是数字：" + Character.isDigit('a'));
        System.out.println("! 是数字：" + Character.isDigit('!'));

        System.out.println("A 是大写字母：" + Character.isUpperCase('A'));
        System.out.println("a 是大写字母：" + Character.isUpperCase('a'));
        System.out.println("! 是大写字母：" + Character.isUpperCase('!'));
        System.out.println("1 是大写字母：" + Character.isUpperCase('1'));

        System.out.println("A 是小写字母：" + Character.isLowerCase('A'));
        System.out.println("a 是小写字母：" + Character.isLowerCase('a'));
        System.out.println("1 是小写字母：" + Character.isLowerCase('1'));

        // 字符转字符串
        String str2 = "" + 'b';
        System.out.println(str2);
        String charToString = Character.toString('c');
        String fromChar = new String(new char[]{'b'});
        String valueOfchar = String.valueOf('B');
        System.out.println(valueOfchar);

        // 大写字符转小写字符
        /*if (c >= 'A' && c <= 'Z') {
            c += 32;
            */

        Character ch = 'a';
        System.out.println("cha:" + ch.toString());
        System.out.println("int:" + (int) ch);
        int inta = 97;
        System.out.println(new StringBuilder().append((char) inta));

    }

    public static void main(String[] args) {
        new CharUtils().CharaterUtil();
    }

}
