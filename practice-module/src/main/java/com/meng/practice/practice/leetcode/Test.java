package com.meng.practice.practice.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        Map<ListNode, Integer> mapA = new HashMap<>();
        mapA.put(headA, headA.val);
        Map<ListNode, Integer> mapB = new HashMap<>();
        mapB.put(headB, headB.val);

        while (headA != null || headB != null) {
            if (headA != null)
            {
                if (mapB.containsKey(headA)) {
                    return headA;
                }
                mapA.put(headA, 0);
                headA = headA.next;
            }
            if (headB != null){
                if (mapA.containsKey(headB)) {
                    return headB;
                }
                mapB.put(headB, 0);
                headB = headB.next;
            }
        }
        return null;
    }

    //https://leetcode.cn/problems/minimum-index-sum-of-two-lists/description/
    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }
        int maxIndexAdd = Integer.MAX_VALUE;
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < list2.length; i++) {
            String name = list2[i];
            if (map.containsKey(name)) {
                if ((map.get(name) + i) < maxIndexAdd) {
                    maxIndexAdd = map.get(name) + i;
                    ans.clear();
                    ans.add(name);
                } else if ((map.get(name) + i) == maxIndexAdd) {
                    ans.add(name);
                }
            }
        }
        String[] ret = ans.toArray(new String[0]);
        return ret;
    }


}
