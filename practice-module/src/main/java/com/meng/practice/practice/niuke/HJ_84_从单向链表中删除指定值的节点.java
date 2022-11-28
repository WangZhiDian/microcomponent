package com.meng.practice.practice.niuke;


import java.util.Scanner;

public class HJ_84_从单向链表中删除指定值的节点 {

    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val) {
            this.val = val;
            next = null;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String input = in.nextLine();
            String[] arr = input.split(" ");
            int target = Integer.parseInt(arr[arr.length - 1]);
            ListNode head = new ListNode(-1);
            //ListNode cur = head;
            ListNode first = new ListNode(Integer.parseInt(arr[1]));
            head.next = first;
            //cur.next = first;
            //cur = first;
            for (int i = 2; i < arr.length - 2; i += 2) {
                ListNode node = new ListNode(Integer.parseInt(arr[i]));
                int pre = Integer.parseInt(arr[i + 1]);
                ListNode cur = head;
                while(cur != null) {
                    if (cur.val == pre) {
                        if(cur.next == null) {
                            cur.next = node;
                        } else{
                            node.next = cur.next;
                            cur.next = node;
                        }
                        break;
                    }
                    cur = cur.next;
                }
            }
            ListNode cur = head.next;
            ListNode pre = head;
            while (cur != null) {
                if (cur.val == target) {
                    pre.next = cur.next;
                    cur = pre.next;
                } else{
                    pre = pre.next;
                    cur = cur.next;
                }
            }

            while(head.next != null) {
                System.out.print(head.next.val + " ");
                head.next = head.next.next;
            }
        }
    }

}
