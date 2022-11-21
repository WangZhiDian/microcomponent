package com.meng.practice.practice.leetcode.towpoint;

public class A_26_删除有序数组中的重复项 {

    /*
    *https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/
    * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。

由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。更规范地说，如果在删除重复项之后有 k 个元素，那么 nums 的前 k 个元素应该保存最终结果。

将最终结果插入 nums 的前 k 个位置后返回 k 。

不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

判题标准:

系统会用下面的代码来测试你的题解:

int[] nums = [...]; // 输入数组
int[] expectedNums = [...]; // 长度正确的期望答案

int k = removeDuplicates(nums); // 调用

assert k == expectedNums.length;
for (int i = 0; i < k; i++) {
    assert nums[i] == expectedNums[i];
}
如果所有断言都通过，那么您的题解将被 通过。



示例 1：

输入：nums = [1,1,2]
输出：2, nums = [1,2,_]
解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
示例 2：

输入：nums = [0,0,1,1,1,2,2,3,3,4]
输出：5, nums = [0,1,2,3,4]
解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。


提示：

1 <= nums.length <= 3 * 104
-104 <= nums[i] <= 104
nums 已按 升序 排列

    * */


    public static int removeDuplicates(int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }
        int indexLeft = 0;
        for (int i = 0; i < nums.length; i++) {
            int left = nums[indexLeft];
            if (left < nums[i]) {
                nums[++indexLeft] = nums[i];
            }
        }
        return indexLeft + 1;
    }

    /*https://leetcode.cn/problems/remove-element/submissions/233256884/
    *
    * 27. 移除元素
    * */
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int indexLeft = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                continue;
            }
            nums[indexLeft] = nums[i];
            indexLeft++;
        }
        return indexLeft;
    }


    /*
    * https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/description/
    *80. 删除有序数组中的重复项 II
    * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
    不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
    *
    * 和26的区别是，数组中的数字，可以重复出现最多两次，在26中只能出现一次
    * */
    public static int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int indexLeft = 0;
        for (int i = 0; i < nums.length; i++) {
            int left = nums[indexLeft];
            if (left < nums[i]) {
                nums[indexLeft + 1] = nums[i];
                indexLeft++;
            } else if (left == nums[i]) {
                // 处理index=1时候的边界
                if (indexLeft == 0 && i != 0) {
                    nums[++indexLeft] = nums[i];
                    continue;
                }
                // 比较前一个数字是不是和left相同，即只能存在两个一样的
                if (indexLeft == 0 || nums[indexLeft - 1] == left) {
                    continue;
                } else {
                    nums[++indexLeft] = nums[i];
                }
            }
        }
        return indexLeft + 1;
    }

    public static void main(String[] args) {
        int[] input = {1,1,1,2,2};
        int ret = removeDuplicates2(input);
        System.out.println(ret);
    }




}
