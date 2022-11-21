package com.meng.practice.practice.niuke;

import com.meng.practice.practice.leetcode.ListNode;

import java.util.HashSet;
import java.util.Set;

public class BM4_合并两个排序的链表 {

    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();

        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }


}
