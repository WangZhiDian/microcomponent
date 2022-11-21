package com.meng.practice.practice.leetcode;

import java.util.Stack;

public class A_20_有效的括号 {

    /*
    *
    * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。

有效字符串需满足：

左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
每个右括号都有一个对应的相同类型的左括号。


示例 1：

输入：s = "()"
输出：true
示例 2：

输入：s = "()[]{}"
输出：true
示例 3：

输入：s = "(]"
输出：false


提示：

1 <= s.length <= 104
s 仅由括号 '()[]{}' 组成

https://leetcode.cn/problems/valid-parentheses/description/
    * */

    public static boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <= s.length() - 1; i++) {
            char ch = s.charAt(i);
            if (stack.isEmpty()) {
                stack.push(ch);
                continue;
            }
            char chPeek = stack.peek();
            if ((chPeek == '(' && ch == ')') || (chPeek == '[' && ch == ']') || (chPeek == '{' && ch == '}')) {
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String input = "()[]{}";
        boolean ret = isValid(input);
        System.out.println(ret);

    }

}
