package com.meng.practice.practice.leetcode;

import java.util.*;

public class A_219_存在重复元素2 {
    //https://leetcode.cn/problems/contains-duplicate-ii/description/
    //219. 存在重复元素 II

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

/*        for (int i = 0; i <= nums.length - 2; i++) {
            for (int j = i + 1; j <= nums.length - 1; j++) {
                if (nums[i] == nums[j] && (j - i) <= k) {
                    return true;
                }
            }
        }*/


        //TreeMap<Integer, Integer> map2 = new TreeMap<>(Comparator.comparingInt(t -> t));
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int[] item = new int[]{i, nums[i]};
            list.add(item);
        }
        list.sort((t1, t2) -> t2[1] - t1[1]);
        int left = 0;
        int right = 1;
        while (right < list.size()) {
            int[] scoreLeft = list.get(left);
            int[] scoreRight = list.get(right);
            if (scoreLeft[1] != scoreRight[1] ) {
                left = right;
                right++;
                continue;
            }
            if (Math.abs(scoreRight[0] - scoreLeft[0]) <= k) {
                return true;
            }
            left++;
            right++;
        }

        return false;
    }


    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], i);
                continue;
            }
            int preIndex = map.get(nums[i]);
            if ((i - preIndex) <= k) {
                return true;
            } else {
                map.put(nums[i], i);
            }
        }
        return false;
    }

    public boolean containsDuplicate(int[] nums) {
        // https://leetcode.cn/problems/contains-duplicate/description/
        if (nums == null || nums.length < 2) {
            return true;
        }

        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.contains(nums[i])) {
                set.add(nums[i]);
            } else {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {

        int[] input = new int[]{1,0,1,1};
        int score = 1;
        boolean ret = new A_219_存在重复元素2().containsNearbyDuplicate2(input, score);
        System.out.println(ret);

    }

}
