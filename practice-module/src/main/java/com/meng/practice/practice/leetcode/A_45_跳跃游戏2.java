package com.meng.practice.practice.leetcode;

public class A_45_跳跃游戏2 {
    // https://leetcode.cn/problems/jump-game-ii/description/
    /*
45. 跳跃游戏 II
中等
1.9K
相关企业
给你一个非负整数数组 nums ，你最初位于数组的第一个位置。

数组中的每个元素代表你在该位置可以跳跃的最大长度。

你的目标是使用最少的跳跃次数到达数组的最后一个位置。

假设你总是可以到达数组的最后一个位置。
示例 1:

输入: nums = [2,3,1,1,4]
输出: 2
解释: 跳到最后一个位置的最小跳跃数是 2。
     从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
示例 2:

输入: nums = [2,3,0,1,4]
输出: 2


提示:

1 <= nums.length <= 104
0 <= nums[i] <= 1000
*/

    public static int jump(int[] nums) {
        if (nums == null || nums.length == 1) {
            return 0;
        }
        int step = 0; // 步长
        int length = nums.length;
        for (int i = 0; i < length; ) {
            //step++;
            int nextIndex = i; // 给个初始化，无意义
            if (nextIndex + 1 >= length - 1) {
                return step + 1;
            }
            if ((i + nums[i]) >= length - 1) {
                return step + 1;
            }


            int willNextMax = -1;
            for (int j = i + 1; j <= i + nums[i]; j++) {
                int willNext = j + nums[j]; // 计算跳过去后，再下一步能跳多远
                if (willNext > willNextMax) {
                    nextIndex = j; // i 下一步应该跳到的位置
                    willNextMax = willNext;
                }
            }
            i = nextIndex;
            step += 1;
        }
        return step;
    }


    //https://leetcode.cn/problems/jump-game/description/
    //55. 跳跃游戏
    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return true;
        }
/*        int length = nums.length - 1;
        int farest = 0;
        for (int i = 0; i < nums.length; i++) {
            int next = nums[i] + i;
            if (next >= length) {
                return true;
            }
            // 如果下一步就是最远这一步，并且最远的这个位置等于0不会再跳动了，则永远也到不了最后
            if (next == farest && nums[i] == 0) {
                return false;
            } else if (next > farest) {
                farest = next;
            }
        }*/


        int cover = 0;
        for(int i = 0; i <= cover; i++){
//nums[i]+i 是因为当前的最大覆盖范围等于从当前元素的位置加上当前元素可以跳跃的最大长度，是从i这个位置开始起跳的。可以画个图更容易理解
            cover = Math.max(i + nums[i] , cover);
            if(cover >= nums.length-1){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        //int[] input = new int[]{2,3,0,1,4};
        //int[] input = new int[]{1,2};
        //int[] input = new int[]{1, 2, 3};
        //int[] input = new int[]{2,3,1,1,4};
        int[] input = new int[]{3,2,1,0,4};
        //int[] input = new int[]{2,1,2,2,1,2,2,2};
        //int ret = jump(input);
        boolean ret = canJump(input);
        System.out.println(ret);
    }

}
