package com.meng.practice.practice.leetcode;

public class A_7_整数反转 {

    // https://leetcode.cn/problems/reverse-integer/description/
    public static int reverse(int x) {
        if (x == 0)
            return x;
        String numStr = "";
        String maxStr = Integer.MAX_VALUE + "";
        String minStr = Integer.MIN_VALUE + "";
        if (x > 0) {
            numStr = x + "";
            String numReverse = new StringBuffer(numStr).reverse().toString();
            numReverse = numReverse.replaceAll("^(0+)", ""); // 去掉前面的0
            if (numReverse.length() == maxStr.length() && numReverse.compareTo(maxStr) > 0) {
                return 0;
            } else {
                return Integer.parseInt(numReverse);
            }
        }

        if (x < 0){
            numStr = x + "";
            numStr = numStr.substring(1, numStr.length());
            String numReverse = new StringBuffer(numStr).reverse().toString();
            numReverse = "-" + numReverse.replaceAll("^(0+)", ""); // 去掉前面的0
            if (numReverse.length() == minStr.length() && numReverse.compareTo(minStr) > 0) {
                return 0;
            } else {
                return Integer.parseInt(numReverse);
            }
        }
        return 0;
    }

    public static int reverse2(int x) {
        int res = 0;
        while(x!=0){
            // 该处无溢出是由于超过了int表示返回，数字正负会反转，个位数将变化
            res = res * 10+x % 10;
            if(res % 10 != x % 10){
                return 0;
            }
            x=x/10;
        }
        return res;
    }

    public static void main(String[] args) {
        int ret = reverse(2147483647);

        System.out.println(ret);
        int a = Integer.reverse(2147483647);
        System.out.println(a);
    }


}
