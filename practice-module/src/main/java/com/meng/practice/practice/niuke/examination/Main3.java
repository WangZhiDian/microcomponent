package com.meng.practice.practice.niuke.examination;


import java.util.*;

public class Main3 {

    /*
    MIIM
    2

    MIM
    1

    M
    -1

    MMM
    -1

    I
    0
    * */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            String input = in.nextLine();
            if (!input.contains("I")) {
                System.out.println(-1);
                continue;
            }
            if (!input.contains("M")) {
                System.out.println(0);
                continue;
            }
            int count = 0;
            boolean[] flag = new boolean[input.length()];
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch == 'I') {
                    continue;
                }
                if (i == 0) {// 处理第一个字符
                    if (i + 1 < input.length()) {
                        if (input.charAt(i + 1) == 'M') {
                            count = -1;
                            break;
                        } else {
                            flag[i + 1] = true;
                            count++;
                            continue;
                        }
                    } else {
                        count = -1;
                        break;
                    }
                } else { // 处理当前字符
                    if (flag[i - 1]) { // 当前字符前一个字符为空，已经放了机柜
                        continue;
                    } else { // 处理当前字符的后一个字符
                        if (input.charAt(i - 1) == 'I') { // 先看一下前面是否可以设置true
                            flag[i - 1] = true;
                            count++;
                            continue;
                        }
                        if (i == input.length() - 1) {// 已经是最后一个字符，且后续没空位,不合法
                            count = -1;
                            break;
                        }
                        if (input.charAt(i + 1) == 'M') { //后一个字符为机器，则出现MMM的形式，不合法
                            count = -1;
                            break;
                        }
                        flag[i + 1] = true;
                        count++;
                        continue;
                    }
                }
            }
            System.out.println(count);
        }
    }



}
