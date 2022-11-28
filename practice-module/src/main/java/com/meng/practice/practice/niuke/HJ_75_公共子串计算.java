package com.meng.practice.practice.niuke;

import java.util.*;

public class HJ_75_公共子串计算 {

    // 注意类名必须为 Main, 不要有任何 package xxx 信息
        private Integer[][] memo;
        private int res = 0;

        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            while (in.hasNextLine()) { // 注意 while 处理多个 case
                String a = in.nextLine();
                String b = in.nextLine();
                //Main m = new Main();

                System.out.println(lcs(a, b));
            }
        }
        public static int lcs(String a, String b) {
            int m = a.length();
            int n = b.length();
            int max = 0;
//        dp[i][j] : a[i-1]结尾和b[j-1]结尾 的最长公共连续子串
            int[][] dp = new int[m + 1][n + 1];
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        max = Math.max(max, dp[i][j]);
                    }else {
                        dp[i][j] = 0;
                    }
                }
            }
            return max;
        }


}
