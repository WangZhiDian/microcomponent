package com.meng.practice.practice.leetcode;

public class A_12_整数转罗马数字 {

    //https://leetcode.cn/problems/integer-to-roman/
    //12. 整数转罗马数字

    public String intToRoman(int num) {

        int[] nums = new int[] {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] strs = new String[] {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < nums.length; i++) {
            while (num >= nums[i]) {
                ans.append(strs[i]);
                num = num - nums[i];
            }
        }
        return ans.toString();

    }

    public static void main(String[] args) {
        String ret = new A_12_整数转罗马数字().intToRoman(3);
        System.out.println(ret);
    }





}
