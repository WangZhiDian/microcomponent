package com.meng.practice.practice.niuke.examination;


import java.util.Scanner;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main1 {

    /*
5
2 2 2 2 2 5 2 2 2
3 3 3 3 3 1 3 3 3
*/

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int storeVal = Integer.parseInt(in.nextLine());
            String[] fileIndex = in.nextLine().split(" ");
            String[] fileVal = in.nextLine().split(" ");
            Map<Integer, Integer> mapFileValue = new HashMap<>();
            Map<Integer, Integer> mapFileNum = new HashMap<>();
            for (int i = 0; i < fileIndex.length; i++) {
                int fileIndexInt = Integer.parseInt(fileIndex[i]);
                mapFileValue.put(fileIndexInt, Integer.parseInt(fileVal[i]));
                if (mapFileNum.containsKey(fileIndexInt)) {
                    mapFileNum.put(fileIndexInt, mapFileNum.get(fileIndexInt) + 1);
                } else {
                    mapFileNum.put(fileIndexInt, 1);
                }
            }
            AtomicInteger sum = new AtomicInteger();
            mapFileNum.forEach((k, v) -> {
                int store = storeVal + mapFileValue.get(k);
                int scan = mapFileValue.get(k) * v;
                int min = store < scan ? store: scan;
                sum.addAndGet(min);
            });
            System.out.println(sum);
        }
    }


}
