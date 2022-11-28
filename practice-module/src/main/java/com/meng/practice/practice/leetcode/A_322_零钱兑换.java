package com.meng.practice.practice.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_322_零钱兑换 {

    static int ans = Integer.MAX_VALUE;

    public static int coinChange(int[] coins, int amount) {

        if (coins == null || coins.length == 0 || amount < 0){
            return -1;
        }
        Arrays.sort(coins);
        change(coins, amount, 0);
        if (ans == Integer.MAX_VALUE) {
            ans = -1;
        }
        System.out.println("ans:" + ans);
        return ans;
    }

    private static boolean find = false;
    private static List<Integer> list = new ArrayList<>();
    private static void change(int[] coins, int amount, int count) {
        if (amount < 0) {
            return;
        }
        if (amount == 0) {
            ans = ans < count ? ans : count;
            find = true;
        }
        if (count >= ans) {
            return;
        }
//        if (find) {
//            return;
//        }
        for (int i = coins.length - 1; i >= 0; i--) {
            list.add(coins[i]);
            change(coins, amount - coins[i], count + 1);
            list.remove(list.size() - 1);
            if (find) {
                return;
            }
        }
    }

    public static void main(String[] args) {
//        int[] input = new int[]{1, 1,2147483647};
//        int coin = 2;
        //int[] input = new int[]{186,419,83,408};
        //int coin = 6249;
        int[] input = new int[1];
        input[0] = 2;
        int coin = 3;
        coinChange(input, coin);
    }

}
