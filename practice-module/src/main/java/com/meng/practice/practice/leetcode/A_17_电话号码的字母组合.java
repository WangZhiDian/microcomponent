package com.meng.practice.practice.leetcode;

import java.util.*;

public class A_17_电话号码的字母组合 {

    /*
给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。

给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
示例 1：

输入：digits = "23"
输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
示例 2：

输入：digits = ""
输出：[]
示例 3：

输入：digits = "2"
输出：["a","b","c"]
https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/
17. 电话号码的字母组合
    * */

    private static HashMap<Character, String> map = new HashMap<Character, String>() {
        {
            put('2', "abc"); put('3', "def"); put('4', "ghi"); put('5', "jkl"); put('6', "mno"); put('7', "pqrs");
            put('8', "tuv"); put('9', "wxyz");
        }
    };

    static List<String> ans = new ArrayList<>();

    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.equals("")) {
            return ans;
        }
        combination(digits, "", 0);
        return ans;
    }

    private static void combination(String digits, String preStr, int index) {

        char ch = digits.charAt(index);
        String chars = map.get(ch);
        if (index == (digits.length() - 1)) {
            for (int i = 0; i < chars.length(); i++) {
                ans.add(preStr + chars.substring(i, i+1));
            }
            return;
        }
        for (int i = 0; i < chars.length(); i++) {
            String temp = preStr + chars.substring(i, i+1);
            combination(digits, temp, index + 1);
        }
    }


    // https://leetcode.cn/problems/combination-sum/description/
    // 39. 组合总和

    static List<List<Integer>> ansList = new ArrayList<>();
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return ansList;
        }
        List<Integer> list = new ArrayList<>();
        combination(candidates, target, 0, list, 0);

        return ansList;
    }

    // while 有问题，还是需要使用for，方向不能错，思路错了，会很惨
    private static void combination(int[] candidates, int target, int index, List<Integer> preList, int preSum) {

        if (index >= candidates.length) {
            return;
        }
        if (preSum + candidates[index] == target) {
            ansList.add(new ArrayList<>(preList));
            return;
        }

        //注意：每一个数字可以使用很多次，条件限制candidats为正数
        int tempSum = preSum;
        int cur = candidates[index];
        while (tempSum <= target) {
            int innerSum = tempSum + cur;
            if (innerSum > target) {
                return;
            }
            preList.add(cur);
            combination(candidates, target, index + 1, preList, innerSum);
            preList.remove(preList.size() - 1);
            tempSum = innerSum;
        }
    }

    public static void main(String[] args) {

        /*String input = "";
        List<String> ret = letterCombinations(input);
        ret.forEach(t -> System.out.print(t + "  "));*/

        int[] input = {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>>  ret = combinationSum(input, target);
        ret.forEach(t -> {
            t.forEach(t1 -> System.out.print(t1 + "  "));
            System.out.println("");
        });
    }

}
