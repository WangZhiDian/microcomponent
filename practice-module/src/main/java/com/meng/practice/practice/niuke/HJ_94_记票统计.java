package com.meng.practice.practice.niuke;

import java.util.*;

public class HJ_94_记票统计 {

    public static void main2(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            int num = Integer.parseInt(in.nextLine());
            Map<String, Integer> map = new HashMap<>();
            List<String> nameList = new ArrayList<>();
            String[] names = in.nextLine().split(" ");
            for (int i = 0; i < num; i++) {
                map.put(names[i], 0);
                nameList.add(names[i]);
            }
            int other = 0;
            int ticket_num = Integer.parseInt(in.nextLine());
            String[] ticket = in.nextLine().split(" ");
            for (int i = 0; i < ticket_num; i++) {
                if (map.containsKey(ticket[i])) {
                    map.put(ticket[i], map.get(ticket[i]) + 1);
                } else {
                    other++;
                }
            }
            nameList.forEach(t -> System.out.println(t + " : " + map.get(t)));
            System.out.println("Invalid : " + other);


        }
        //List<String> res = new ArrayList<>();

    }


    public static void main3(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            List<String> res = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            int maxLength = 0;
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (!Character.isDigit(ch)) {

                    int tempLength = sb.length();
                    if (tempLength == maxLength) {
                        res.add(sb.toString());
                    } else if(tempLength > maxLength) {
                        maxLength = tempLength;
                        res.clear();
                        res.add(sb.toString());
                    }
                    sb.setLength(0);
                } else {
                    sb.append(ch);
                }
            }
            if (sb.length() > maxLength) {
                System.out.println(sb.toString() + "," + sb.length());
            } else if (sb.length() == maxLength) {
                res.forEach(t -> System.out.print(t));
                System.out.print(sb.toString() + "," + sb.length());
            } else {
                res.forEach(t -> System.out.print(t));
                System.out.print("," + maxLength);
            }

        }
    }

    public static void main4(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            String[] arr = input.split("\\.");
            if (arr.length != 4) {
                System.out.println("NO");
                continue;
            }
            boolean ligal = true;
            for (int i = 0; i < 4; i++) {
                if ("".equals(arr[i])) {
                    ligal = false;
                    break;
                }
                int addr = Integer.parseInt(arr[i]);
                if (addr < 0 || addr > 255) {
                    ligal = false;
                    break;
                }
            }

            if (ligal) {
                System.out.println("YES");
            } else{
                System.out.println("NO");
            }
        }
    }

    public static void main5(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            Map<Character, Integer> map = new HashMap<>();
            for(int i = 0; i < input.length(); i++) {
                map.merge(input.charAt(i), 1, (a, b) -> a + 1);
            }
            List<int[]> res = new ArrayList<>();
            map.forEach((k, v) -> {
                res.add(new int[]{k, v});
            });
            res.sort((t1, t2) -> {
                if (t1[1] != t2[1])
                    return t1[1] - t2[1];
                return t1[0] - t2[0];
            });
            res.forEach(t -> System.out.print((char)t[0]));
        }
    }

    public static void main6(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            int count = 0;
            for (int i = 0; i < a; i++) {
                int tmp = i * i;
                if ((tmp + "").endsWith(i + "")) {
                    count++;
                    System.out.println(i);
                }
            }
            System.out.print(count);
        }
    }

    public static void main7(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            String str = Integer.toBinaryString(a);
            String[] arr = str.split("0");
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(arr));
            list.sort((t1, t2) -> t2.length() - t1.length());
            System.out.print(list.get(0).length());

            HashMap map = new HashMap();
            Set set = map.keySet();
        }
    }

    private static Map<String, String> map = new HashMap<>();
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                Character ch = input.charAt(i);
                if (Character.isDigit(ch)) {
                    sb.append(ch);
                    continue;
                }
                if (Character.isUpperCase(ch)) {
                    if (ch == 'Z') {
                        sb.append("a");
                    } else{
                        sb.append((char)(ch + 1 + 32));
                    }
                    continue;
                }
                if (Character.isLowerCase(ch)) {
                    map.keySet().forEach(key -> {
                        if (key.contains(ch.toString())) {
                            sb.append(map.get(key));
                        }
                    });
                }
            }
            System.out.print(sb.toString());
        }
    }

}
