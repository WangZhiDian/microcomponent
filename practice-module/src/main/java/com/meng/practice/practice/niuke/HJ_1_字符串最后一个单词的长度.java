package com.meng.practice.practice.niuke;

import java.util.*;

public class HJ_1_字符串最后一个单词的长度 {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
/*        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }*/


        /*while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            String ch = in.next();
            int ret = HJ2(input, ch);
            System.out.println(ret);
        }*/
/*
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            List<String> ret = HJ4(input);
            ret.forEach(t -> System.out.println(t));
        }*/

        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            HJ23(input);
        }

        /*while (in.hasNext()) { // 注意 while 处理多个 case
            int num = Integer.parseInt(in.nextLine());
            List<int[]> list = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                String line = in.nextLine();
                list.add(new int[]{Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1])});
            }
            HJ8(list);
        }*/
    }

    // https://www.nowcoder.com/practice/8c949ea5f36f422594b306a2300315da?tpId=37&tqId=21224&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
    // HJ1 字符串最后一个单词的长度
    private static int calcLength(String str) {
        String[] strArr = str.split(" ");
        return strArr[strArr.length - 1].length();
    }

    //https://www.nowcoder.com/practice/a35ce98431874e3a820dbe4b2d0508b1?tpId=37&tqId=21225&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
    // HJ2 计算某字符出现次数
    private static int HJ2(String str, String ch) {
        str = str.toLowerCase();
        ch = ch.toLowerCase();
        String str2 = str.replaceAll(ch, "");
        return str.length() - str2.length();
    }

    // https://www.nowcoder.com/practice/d9162298cb5a437aad722fccccaae8a7?tpId=37&tqId=21227&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
    // HJ4 字符串分隔
    private static List<String> HJ4(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.equals("")) {
            return ans;
        }
        //String tempStr = "";
        while (str.length() > 0) {
            if (str.length() >= 8) {
                ans.add(str.substring(0, 8));
                str = str.substring(8);
            } else {
                String temp = "00000000";
                ans.add(str + temp.substring(str.length() - 1, temp.length()));
                break;
            }
        }
        return ans;
    }

    // https://www.nowcoder.com/practice/8f3df50d2b9043208c5eed283d1d4da6?tpId=37&rp=1&ru=%2Fexam%2Foj%2Fta&qru=%2Fexam%2Foj%2Fta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D37&difficulty=&judgeStatus=&tags=&title=&gioEnter=menu
    // HJ5 进制转换
    private static int HJ5(String input) {
        input = input.substring(2);
        int ret = Integer.parseInt(input, 16);
        return ret;
    }

    //https://www.nowcoder.com/practice/196534628ca6490ebce2e336b47b3607?tpId=37&rp=1&ru=%2Fexam%2Foj%2Fta&qru=%2Fexam%2Foj%2Fta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D37&difficulty=&judgeStatus=&tags=&title=&gioEnter=menu
    //HJ6 质数因子
    private static int HJ6(int input) {



        return 0;
    }

    // https://www.nowcoder.com/practice/de044e89123f4a7482bd2b214a685201?tpId=37&tqId=21231&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
    // HJ8 合并表记录
    private static int HJ8(List<int[]> list) {

        Map<Integer, Integer> map = new HashMap<>();
        list.forEach(item -> {
            if (map.containsKey(item[0])) {
                 map.put(item[0], item[1] + map.get(item[0]));
            } else {
                map.put(item[0], item[1]);
            }
        });
        List<int[]> ans = new ArrayList<>();
        map.forEach((k, v) -> ans.add(new int[]{k, v}));
        ans.sort(Comparator.comparingInt(t -> t[0]));
        ans.forEach(t -> System.out.println(t[0] + " " + t[1]));
        return 0;
    }

    // https://www.nowcoder.com/practice/eb94f6a5b2ba49c6ac72d40b5ce95f50?tpId=37&tqId=21233&rp=1&ru=/exam/oj/ta&qru=/exam/oj/ta&sourceUrl=%2Fexam%2Foj%2Fta%3FtpId%3D37&difficulty=undefined&judgeStatus=undefined&tags=&title=
    // HJ10 字符个数统计
    private static void HJ10(String input) {
        if (input == null || input.equals("")) {
            System.out.println(0);
            return;
        }
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            set.add(input.charAt(i));
        }
        System.out.println(set.size());

        Map<Character, Integer> map = new TreeMap<>((t1, t2) -> t1 - t2);
    }

    static int min = Integer.MAX_VALUE;
    private static void HJ23(String a) {

        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < a.length(); i++) {
            char ch = a.charAt(i);
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else{
                map.put(ch, 1);
            }
            //min = min <= map.get(ch)? min: map.get(ch);
        }
        map.forEach((k, v) -> {
            min = min < v?min: v;
        });

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < a.length(); i++) {
            if(map.get(a.charAt(i)) != min) {
                sb.append(a.charAt(i));
            }
        }
        System.out.print(sb.toString());
    }




}
