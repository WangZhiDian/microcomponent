package com.meng.practice.practice.leetcode.shuzu;

public class Array_2_两数相加 {

    /**
     * 中等 uri： https://leetcode.cn/problems/add-two-numbers/
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     *
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     *
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     *
     示例1 ：
     输入：l1 = [2,4,3], l2 = [5,6,4]
     输出：[7,0,8]
     解释：342 + 465 = 807.

     示例 2：
     输入：l1 = [0], l2 = [0]
     输出：[0]


     示例 3：
     输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     输出：[8,9,9,9,0,0,0,1]

     * */


     public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
      }


    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {

        ListNode result = new ListNode(0, null);
        ListNode head = result;

        int carry = 0;
        while (l1 != null || l2 != null) {
            int first = 0;
            if (l1 != null) {
                first = l1.val;
                l1 = l1.next;
            }
            int second = 0;
            if (l2 != null) {
                second = l2.val;
                l2 = l2.next;
            }
            int num = (carry + first + second) % 10;
            carry = (carry + first + second) / 10;
            ListNode node = new ListNode(num, null);
            result.next = node;
            result = result.next;
        }
        if (carry != 0) {
            ListNode node = new ListNode(carry, null);
            result.next = node;
        }
        return head.next;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode result;
        ListNode head = new ListNode(0, null);
        ListNode cur = head;

        int carry = 0;
        while (l1 != null || l2 != null) {
            result = null;
            int first = 0;
            if (l1 != null) {
                first = l1.val;
                result = l1;
                l1 = l1.next;
            }
            int second = 0;
            if (l2 != null) {
                second = l2.val;
                if (result == null) {
                    result = l2;
                }
                l2 = l2.next;
            }
            int num = (carry + first + second) % 10;
            carry = (carry + first + second) / 10;
            result.val = num;
            cur.next = result;
            cur = result;
        }
        if (carry != 0) {
            ListNode node = new ListNode(carry, null);
            cur.next = node;
        }
        return head.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3, null)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4, null)));
        ListNode ret = addTwoNumbers(l1, l2);
    }

}
