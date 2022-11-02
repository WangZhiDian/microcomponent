package com.meng.practice.practice.leetcode.shuzu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Array_1_两数之和 {

    /**
     * 简单： uri: https://leetcode.cn/problems/two-sum/
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     *
     * 你可以按任意顺序返回答案。
     *
     *  示例 1：
     *
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * 示例 2：
     *
     * 输入：nums = [3,2,4], target = 6
     * 输出：[1,2]
     * 示例 3：
     *
     * 输入：nums = [3,3], target = 6
     * 输出：[0,1]
     *
     * */


    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numIndexLocation = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            numIndexLocation.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int subscribe = target - num;
            if (numIndexLocation.containsKey(subscribe) && i != numIndexLocation.get(subscribe)) {
                return new int[]{i, numIndexLocation.get(subscribe)};
            }
//            Integer anOther = numIndexLocation.get(subscribe);
//            if (anOther == null || i == anOther) {
//                continue;
//            }
//            return new int[]{i, anOther};
        }
        return new int[0];
    }

    public static void main(String[] args) {
        // int[] in = new int[]{2,7,11,15};
        int[] in = new int[]{1, 3, 2, 4};
        int[] out = twoSum(in, 6);
        Arrays.stream(out).forEach(num -> System.out.println(num));
    }

}
