package com.meng.practice.practice.niuke;

import java.util.*;

public class HJ_43_迷宫问题 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String[] params = in.nextLine().split(" ");
            int row = Integer.parseInt(params[0]);
            int col = Integer.parseInt(params[1]);
            int[][] grid = new int[row][col];
            for(int i = 0; i < row; i++) {
                String[] rowArr = in.nextLine().split(" ");
                for (int j = 0; j < col; j++) {
                    grid[i][j] = Integer.parseInt(rowArr[j]);
                }
            }
            List<int[]> pre = new ArrayList<>();
            dfs(grid, pre, 0, 0);

            List<int[]> ret = ans.get(0);
            ret.forEach(item -> System.out.println("(" + item[0] + "," + item[1] + ")"));
        }
    }
    // 矩阵里边，运行过且需要，暂存2，不需要暂存-1
    private static boolean find = false;
    private static List<List<int[]>> ans = new ArrayList<>();
    private static void dfs(int[][] grid, List<int[]> pre, int row, int col) {

        int rowlength = grid.length;
        int collength = grid[0].length;

        // 如果遇到边，直接删除异常节点
        if(row < 0 || row >= rowlength || col < 0 || col >=collength) {
            //pre.remove(pre.size() - 1);
            return;
        }
        if (find) {
            grid[row][col] = -1;
            return; // 已经找到一条路近，就返回结果
        }
        if (grid[row][col] != 0) {
            return;
        }
        if (row == rowlength - 1 && col == collength - 1) {
            pre.add(new int[]{row, col});
            ans.add(new ArrayList<>(pre));
            pre.remove(pre.size() - 1);
            grid[row][col] = -1;
            find = true;
            return;
        }
        pre.add(new int[]{row, col});
        grid[row][col] = -1;
        dfs(grid, pre, row + 1, col);
        dfs(grid, pre, row - 1, col);
        dfs(grid, pre, row, col + 1);
        dfs(grid, pre, row, col - 1);
        pre.remove(pre.size() - 1);
    }
}
