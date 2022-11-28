package com.meng.practice.practice.datastructure;

public class StringUtils {

    private static void trimStr() {
        // 去掉字符串前面的0 使用"^0+"可以匹配字符串开头的一个或多个"0"，并将其替换为空，可以实现left trim 0的效果。
        String str = "00000002001";
        String newStr = str.replaceAll("^(0+)", "");
        System.out.println(newStr);

        // 去掉字符串后面的0 使用"0+$"可以匹配字符串末尾的一个或多个"0"，并将其替换为空，可以实现right trim 0的效果。
        String str1 = "2100000";
        String newStr1 = str1.replaceAll("0*$", "");
        System.out.println(newStr1);

        // 使用"0+$"|"^0+"可以匹配字符串开头及末尾的一个或多个"0"，并将其替换为空，可以实现trim 0的效果。
    }

    public static String trim(String content, char matchContent) {
        String rightMatch = matchContent + "+$";
        String leftMatch = "^" + matchContent + "+";
        return content.replaceAll(leftMatch + "|" + rightMatch, "");

    }

    public static String rightTrim(String content, char matchContent) {
        return content.replaceAll(matchContent + "+$", "");
    }

    public static String leftTrim(String content, char matchContent) {
        return content.replaceAll("^" + matchContent + "+", "");
    }

    public static void main(String[] args) {


        trimStr();

        String str = "/aab/";
        String newStr = str.replaceAll("^(/+)","");
        System.out.println(newStr);
        String newStr2 = newStr.replaceAll("(/*)$","");
        System.out.println(newStr2);

        String str3 = trim(str, '/');
        System.out.println(str3);

    }



}
