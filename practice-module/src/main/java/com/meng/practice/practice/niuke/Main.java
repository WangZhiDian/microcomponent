package com.meng.practice.practice.niuke;

import java.util.Scanner;
import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int input = Integer.parseInt(in.nextLine());
            String[] strArr = in.nextLine().split(" ");
            int[] train = new int[input];
            boolean[] used = new boolean[input];
            for (int i = 0; i < input; i++) {
                train[i] = Integer.parseInt(strArr[i]);
                //train[i] = i + 1;
                used[i] = false;
            }
            Arrays.sort(train);
            findWay(train, used);
            ans.forEach(t -> {
                t.forEach(t1 -> System.out.print(t1 + " "));
                System.out.println();
            });
        }
    }
    static List<List<Integer>> ans = new ArrayList<>();
    static List<Integer> list = new ArrayList<>();
    private static void findWay(int[] train, boolean[] used) {

        if (list.size() == train.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i = 0; i < train.length; i++) {
            if (!used[i]){
                used[i] = true;
                list.add(train[i]);
                findWay(train, used);
                list.remove(list.size() - 1);
                used[i] = false;
            }
        }

    }


}