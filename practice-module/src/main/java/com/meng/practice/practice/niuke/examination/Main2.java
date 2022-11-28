package com.meng.practice.practice.niuke.examination;

import java.util.*;

public class Main2 {
/*
9
5 2 1 5 2 1 5 2 1

10
5 2 1 5 2 1 5 2 1 6

* */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            String minitesNum = in.nextLine();
            String[] scores = in.nextLine().split(" ");
            int[] scoresInt = new int[scores.length];
            int sumScore = 0;
            int maxSingleScore = 0;
            for (int i = 0; i < scores.length; i++) {
                int score = Integer.parseInt(scores[i]);
                sumScore += score;
                scoresInt[i] = score;
                maxSingleScore = maxSingleScore > score? maxSingleScore: score;
            }
            Arrays.sort(scoresInt);

            for (int i = 1; i <= sumScore; i++) {
                if (i < maxSingleScore) {
                    continue;
                }
                if (sumScore / i > scores.length) {
                    continue;
                }
                if (sumScore % i == 0) {
                    if (isRight(scoresInt, i, sumScore)) {
                        System.out.println(i);
                        break;
                    }
                }
            }
        }
    }

    private static boolean isRight(int[] scores, int minNum, int allScore) {

        int left = 0;
        int right = scores.length - 1;
        int tmp = 0;
        while (left <= right) {
            if (scores[right] == minNum) {
                allScore = allScore - minNum;
                right--;
                continue;
            }
            int add;
            if (left == right) {
                add = scores[right] + tmp;;
            } else {
                add = scores[left] + scores[right] + tmp;
            }
            if (add == minNum) {
                left++;
                right--;
                allScore = allScore - minNum;
                tmp = 0;
                continue;
            }
            if (add < minNum) {
                left ++;
                tmp += scores[left];
                continue;
            }
            tmp += scores[right];
            right--;
        }
        return allScore == 0;
    }




}
