package com.meng.practice.practice.leetcode.shuzu;

import java.util.ArrayList;
import java.util.List;

public class Array_118_杨辉三角 {

    //https://leetcode.cn/problems/pascals-triangle/description/
    public static List<List<Integer>> generate(int numRows) {

        List<List<Integer>> ret = new ArrayList<>();
        int[][] ans = new int[numRows][];
        for (int i = 0; i < numRows; i++) {
            List<Integer> innerList = new ArrayList<>();
            int rowLength = i + 1;
            int[] rowArr = new int[rowLength];
            System.out.println("row:" + i);
            for (int j = 0; j < rowLength; j++) {
                if (i == 0) {
                    rowArr[j] = 1;
                    innerList.add(1);
                    break;
                }

                int leftVal = 0;
                int rightVal = 0;
                int colUp = j - 1;
                int rowUp = i - 1;
                if (rowUp >= 0 && colUp >=0) {
                    leftVal = ans[rowUp][colUp];
                }
                if (rowUp >= 0 && ans[rowUp].length > j) {
                    rightVal = ans[rowUp][j];
                }
                rowArr[j] = leftVal + rightVal;
                innerList.add(leftVal + rightVal);
            }
            ans[i] = rowArr;
            ret.add(innerList);

        }

        ret.forEach(t -> {
            t.forEach(t1 -> System.out.print(t1 + "  "));
            System.out.println();
        });

        return ret;
    }

    public static void main(String[] args) {
        //generate(6);

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        System.out.println(list.get(3));
    }



}
