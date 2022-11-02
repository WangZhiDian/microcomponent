package com.meng.practice.practice.leetcode.zifuchuan;

import java.util.LinkedList;
import java.util.Queue;

public class String_3_无重复字符的最长子串 {


    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     *
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * */

    public static int lengthOfLongestSubstring(String s) {

        if (s == null || s.equals("")) {
            return 0;
        }

        int longestLength = 0;
        Queue<Character> queue = new LinkedList<>();
        int right = 0;
        while (right < s.length()) {

            char ch = s.charAt(right);
            if (queue.contains(ch)) {
                if (longestLength <= queue.size()) {
                    longestLength = queue.size();
                }
                while (!queue.isEmpty()) {
                    char temp = queue.poll();
                    if (temp == ch) {
                        break;
                    }
                }
            }
            queue.offer(ch);
            longestLength = longestLength > queue.size() ? longestLength: queue.size();
            right ++;
        }

        return longestLength;
    }

    public int lengthOfLongestSubstring2(String s) {
        if (s == null){
            return 0;
        }
        int maxLength = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);

            int repeatIndex = sb.toString().indexOf(ch);
            if (repeatIndex > -1){
                int length = sb.length();
                maxLength = length > maxLength? length: maxLength;
                sb.delete(0, repeatIndex + 1);
                sb.append(ch);
                continue;
            }
            sb.append(ch);
        }
        int ret = (sb.length() > maxLength)? sb.length(): maxLength;
        return ret;
    }

    public static void main(String[] args) {
        String input = "pwwkew";
        int ret = lengthOfLongestSubstring(input);
        System.out.println(ret + "  " + "  |");
    }

}
