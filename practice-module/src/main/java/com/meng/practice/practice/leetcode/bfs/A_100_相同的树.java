package com.meng.practice.practice.leetcode.bfs;

import com.meng.practice.practice.leetcode.TreeNode;

public class A_100_相同的树 {

    /*
    * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。

如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
https://leetcode.cn/problems/same-tree/description/
    100. 相同的树
*/
    public boolean isSameTree(TreeNode p, TreeNode q) {

        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {

            return false;
        }
        if (p.val != q.val) {
            return false;
        }

        if (isSameTree(p.left, q.left)) {
            return isSameTree(p.right, q.right);
        } else {
            return false;
        }

    }

    // 700. 二叉搜索树中的搜索
    // https://leetcode.cn/problems/search-in-a-binary-search-tree/description/
    public TreeNode searchBST(TreeNode root, int val) {

        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        if (root.val > val) {
            return searchBST(root.left, val);
        }
        return searchBST(root.right, val);

    }

    // 98. 验证二叉搜索树  二叉搜索树，左节点<跟<又节点
    // https://leetcode.cn/problems/validate-binary-search-tree/
    //
    public boolean isValidBST(TreeNode root) {

        if (root == null) {
            return true;
        }
        if (root.left != null && root.val <= getMaxValue(root.left)) {
            return false;
        }
        if (root.right != null && root.val >= getMinValue(root.right)) {
            return false;
        }
        boolean flag = isValidBST(root.left);
        if (!flag) {
            return false;
        }
        return isValidBST(root.right);
    }

    private int getMaxValue(TreeNode root) {
        if (root.right != null) {
            return getMaxValue(root.right);
        }
        return root.val;
    }

    private int getMinValue(TreeNode root) {
        if (root.left != null) {
            return getMaxValue(root.left);
        }
        return root.val;
    }


    // https://leetcode.cn/problems/insert-into-a-binary-search-tree/
    // 701. 二叉搜索树中的插入操作
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val, null, null);
        }
        if (root.val < val) {
            root.right = insertIntoBST(root.right, val);
        }
        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }

    // 450. 删除二叉搜索树中的节点
    // https://leetcode.cn/problems/delete-node-in-a-bst/
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }

        // 从左子树中找到最大节点，挂载上
        if (root.val == key) {
            if (root.left == null && root.right == null) {
                return null;
            }
            deleteNode1(root.left, root);
        }else if (root.val > key) {
            deleteNode2(root.left, root, key);
        } else {
            deleteNode2(root.right, root, key);
        }
        return root;
    }

    private void deleteNode2(TreeNode node, TreeNode father, int key) {
        if (node == null) {
            return;
        }
        if (node.val == key) {
            deleteNode1(node, father);
        } else if (node.val > key) {
            deleteNode2(node.left, node, key);
        } else {
            deleteNode2(node.right, node, key);
        }
    }

    // node: 需要删除的节点， fatherNode 需要删除的节点的父节点
    private void deleteNode1(TreeNode node, TreeNode fatherNode) {

        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            fatherNode.left = null;
        }
        if (node.right == null && node.left != null) {
            fatherNode.left = node.left;
            return;
        }
        if (node.left == null && node.right != null) {
            fatherNode.left = node.right;
        }
        TreeNode nodeRight = node.right;
        TreeNode nodeLeft = getLeftMaxNode(node.left, node);
        nodeLeft.right = nodeRight;
        if (nodeLeft.val != node.left.val) {
            nodeLeft.left = node.left;
        }
        fatherNode.left = nodeLeft;
    }

    private  TreeNode getLeftMaxNode(TreeNode node, TreeNode fatherNode) {

        if (node == null) {
            return node;
        }
        if (node.right != null) {
            return getLeftMaxNode(node.right, fatherNode);
        } else {
            if (node.left == null) {
                fatherNode.right = null;
                return node;
            } else {
                fatherNode.right = node.left;
                node.left = null;
                return node;
            }
        }
    }

    public static void main(String[] args) {

        TreeNode node2 = new TreeNode(2);
        TreeNode node4 = new TreeNode(4);
        TreeNode node7 = new TreeNode(7);
        TreeNode node3 = new TreeNode(3, node2, node4);
        TreeNode node6 = new TreeNode(6, null, node7);
        TreeNode node5 = new TreeNode(5, node3, node6);
        TreeNode node = new A_100_相同的树().deleteNode(node5, 3);
        System.out.println("aa");
        printTree(node);
    }

    private static void printTree(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + "  ");
        printTree(root.left);
        printTree(root.right);
    }

}
