package com.meng.practice.practice.leetcode;

import java.util.Arrays;

public class A_14_最长公共前缀 {


    /*
    * 编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 ""。



示例 1：

输入：strs = ["flower","flow","flight"]
输出："fl"
示例 2：

输入：strs = ["dog","racecar","car"]
输出：""
解释：输入不存在公共前缀。


提示：

1 <= strs.length <= 200
0 <= strs[i].length <= 200
strs[i] 仅由小写英文字母组成

https://leetcode.cn/problems/longest-common-prefix/description/
*/

    public static String longestCommonPrefix(String[] strs) {

            StringBuilder sb = new StringBuilder();
            Arrays.sort(strs);
            if (strs[0].equals("")) {
                return "";
            }
            if (strs.length == 1) {
                return strs[0];
            }
            String first = strs[0];
            for (int i = 0; i <= first.length() - 1; i++) {
                char ch = first.charAt(i);
                boolean flag = true;
                for (int j = 1; j <= strs.length - 1; j++) {
                    char chj = strs[j].charAt(i);
                    if (ch != chj) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    break;
                }
                sb.append(ch);
            }
            return sb.toString();
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"ab", "a"};
        String ret = longestCommonPrefix(arr);
        System.out.println(ret);
    }

}
