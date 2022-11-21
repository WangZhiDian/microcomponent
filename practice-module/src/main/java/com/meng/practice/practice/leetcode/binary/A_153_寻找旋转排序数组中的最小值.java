package com.meng.practice.practice.leetcode.binary;

public class A_153_寻找旋转排序数组中的最小值 {

    /* 二分查找旋转点，旋转点上的值就是最小值 */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        // 查找旋转点位置
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[right];
    }


}
