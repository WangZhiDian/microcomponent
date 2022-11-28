package com.meng.practice.practice.niuke;


import java.util.*;

public class HJ_17_坐标移动 {

    public static void main2(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String[] strs = in.nextLine().split(";");
            List<int[]> list = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                if (!ligal(strs[i])) {
                    continue;
                }
                String direct = strs[i].substring(0, 1);
                int val = Integer.parseInt(strs[i].substring(1));
                if (direct.equals("A")) {
                    list.add(new int[]{0 - val, 0});
                } else if (direct.equals("D")) {
                    list.add(new int[]{val, 0});
                }else if (direct.equals("W")) {
                    list.add(new int[]{0, 0 - val});
                }else if (direct.equals("S")) {
                    list.add(new int[]{0, val});
                }
            }
            int x = 0;
            int y = 0;
            for (int i = 0; i < list.size(); i++) {
                x += list.get(i)[0];
                y += list.get(i)[1];
            }
            System.out.println(x + "," + y);
        }
    }
    private static String rule = "ADWS";
    private static boolean ligal(String str) {
        if (str == null || "".equals(str) || str.length() >3 || str.length() == 1) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (i == 0) {
                if (rule.indexOf(ch) < 0) {
                    return false;
                }
            } else {
                if (!Character.isDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            if (input == null || input.length() < 8) {
                System.out.println("NG");
                continue;
            }
            int numCount = 0;
            int lower = 0;
            int upper = 0;
            int other = 0;
            boolean ligal = true;
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if(Character.isDigit(ch)) {
                    numCount = 1;
                } else if(Character.isUpperCase(ch)) {
                    upper = 1;
                } else if(Character.isLowerCase(ch)) {
                    lower = 1;
                } else {
                    other = 1;
                }
                if (i <= input.length() - 4) {
                    String s1 = input.substring(i + 2);
                    String s2 = input.substring(i, i + 2);
                    boolean repeat
                            = input.substring(i + 2).contains(input.substring(i, i + 2));
                    if (repeat) {
                        ligal = false;
                        break;
                    }
                }
            }
            if (!ligal) {
                System.out.println("NG");
                continue;
            }
            if ((numCount + upper + lower + other) >= 3) {
                System.out.println("OK");
            } else {
                System.out.println("NG");
            }
        }
    }
}
