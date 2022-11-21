package com.meng.practice.practice.leetcode.tree;

import com.meng.practice.practice.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tree_94_二叉树的中序遍历 {

    /*
    *
    * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
示例 1：


输入：root = [1,null,2,3]
输出：[1,3,2]
示例 2：

输入：root = []
输出：[]
示例 3：

输入：root = [1]
输出：[1]


提示：

树中节点数目在范围 [0, 100] 内
-100 <= Node.val <= 100


进阶: 递归算法很简单，你可以通过迭代算法完成吗？

https://leetcode.cn/problems/binary-tree-inorder-traversal/description/
*/

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        treeReverse(ret, root);
        return ret;
    }

    private void treeReverse(List<Integer> ret, TreeNode node) {
        if (node.left != null) {
            treeReverse(ret, node.left);
        }
        ret.add(node.val);
        if (node.right != null) {
            treeReverse(ret, node.right);
        }
    }

    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }

        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {

            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            ret.add(root.val);
            root = root.right;
        }

        return ret;
    }

}
