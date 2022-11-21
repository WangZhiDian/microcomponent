package com.meng.practice.practice.leetcode;

public class A_9_回文数字 {

    // https://leetcode.cn/problems/palindrome-number/description/
    public boolean isPalindrome(int x) {
        String str = x + "";
        String reverse = new StringBuilder(str).reverse().toString();
        if (str.equals(reverse)){
            return true;
        }else {
            return false;
        }
    }

}
