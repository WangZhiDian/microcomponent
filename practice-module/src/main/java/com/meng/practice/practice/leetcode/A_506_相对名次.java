package com.meng.practice.practice.leetcode;

import java.util.*;

public class A_506_相对名次 {

    // https://leetcode.cn/problems/relative-ranks/
    // 506. 相对名次
    /*
    * 给你一个长度为 n 的整数数组 score ，其中 score[i] 是第 i 位运动员在比赛中的得分。所有得分都 互不相同 。

运动员将根据得分 决定名次 ，其中名次第 1 的运动员得分最高，名次第 2 的运动员得分第 2 高，依此类推。运动员的名次决定了他们的获奖情况：

名次第 1 的运动员获金牌 "Gold Medal" 。
名次第 2 的运动员获银牌 "Silver Medal" 。
名次第 3 的运动员获铜牌 "Bronze Medal" 。
从名次第 4 到第 n 的运动员，只能获得他们的名次编号（即，名次第 x 的运动员获得编号 "x"）。
使用长度为 n 的数组 answer 返回获奖，其中 answer[i] 是第 i 位运动员的获奖情况。



示例 1：

输入：score = [5,4,3,2,1]
输出：["Gold Medal","Silver Medal","Bronze Medal","4","5"]
解释：名次为 [1st, 2nd, 3rd, 4th, 5th] 。
示例 2：

输入：score = [10,3,8,9,4]
输出：["Gold Medal","5","Bronze Medal","Silver Medal","4"]
解释：名次为 [1st, 5th, 3rd, 2nd, 4th] 。


提示：

n == score.length
1 <= n <= 104
0 <= score[i] <= 106
score 中的所有值 互不相同*/
    public String[] findRelativeRanks(int[] score) {
/*        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < score.length; i++) {
            map.put(i, score[i]);
        }*/

        List<Integer> list = new ArrayList<>(score.length);
        for (int i = 0; i < score.length; i++) {
            list.add(score[i]);
        }
        Arrays.sort(score);
        Map<Integer, String> map = new HashMap<>();
        int count = 1;
        for (int i = score.length - 1; i >= 0; i--) {
            if (count == 1) {
                map.put(score[i], "Gold Medal");
                count++;
                continue;
            }else if (count == 2) {
                map.put(score[i], "Silver Medal");
                count++;
                continue;
            } else if (count == 3) {
                map.put(score[i], "Bronze Medal");
                count++;
                continue;
            } else {
                map.put(score[i], count + "");
                count++;
            }
        }
        String[] ret = new String[score.length];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = map.get(list.get(i));
        }

        return ret;
    }

    public static void main(String[] args) {
        int[] input = new int[]{5,4,3,2,1};
        String[] ret = new A_506_相对名次().findRelativeRanks(input);
        Arrays.stream(ret).forEach(t -> System.out.print(t + " |"));
    }

}
