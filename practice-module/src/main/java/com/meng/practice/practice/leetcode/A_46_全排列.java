package com.meng.practice.practice.leetcode;

import java.util.ArrayList;
import java.util.List;

public class A_46_全排列 {

    //https://leetcode.cn/problems/permutations/description/
    /*
    * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
示例 1：

输入：nums = [1,2,3]
输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
示例 2：

输入：nums = [0,1]
输出：[[0,1],[1,0]]*/

    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> output = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ans;
        }
        boolean[] uses = new boolean[nums.length];
        traceBack(nums, uses);
        return res;
    }

    private void traceBack(int[] nums, boolean[] uses) {
        // 返回的结束条件
        if (output.size() == nums.length) {
            res.add(new ArrayList<>(output));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (uses[i]) {
                continue;
            }
            uses[i] = true;
            output.add(nums[i]);
            traceBack(nums, uses);
            uses[i] = false;
            output.remove(output.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] input = new int[]{1, 2, 3};
        List<List<Integer>> res = new A_46_全排列().permute(input);
        res.forEach(t -> {
            t.forEach(t1 -> System.out.print(t1 + " "));
            System.out.println();
        });
    }
}
