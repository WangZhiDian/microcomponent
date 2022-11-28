package com.meng.practice.practice.niuke;

import java.util.*;

public class HJ_22_汽水瓶 {


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
        int release = 0;
        while (in.hasNext()) { // 注意 while 处理多个 case
            int a = Integer.parseInt(in.nextLine());
            if (a == 0) {
                break;
            }
            int count = 0;
            a = a + release;
            while(a >= 3) {
                int b = a / 3;
                count = count + b;
                a = a % 3 + b;
            }
            if (a == 2) {
                count += 1;
                release = 0;
            } else{
                //release = a;
            }
            System.out.println(count);
        }
    }
    


}
