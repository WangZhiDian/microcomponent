package com.meng.practice.practice.leetcode.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

public class A_463_岛屿的周长 {
    // https://leetcode.cn/problems/island-perimeter/
    /*给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
网格中的格子 水平和垂直 方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。

示例 1：

输入：grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
输出：16
解释：它的周长是上面图片中的 16 个黄色的边
示例 2：

输入：grid = [[1]]
输出：4
示例 3：

输入：grid = [[1,0]]
输出：4
463. 岛屿的周长
*/

    public int islandPerimeter(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int row = grid.length;
        int col = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if (grid[i][j] == 1) {
                    return dfs(grid, i, j);
                }

            }
        }

        return 0;
    }

    // 深度优先算法
    private int dfs(int[][] grid, int row, int col) {

        // 如果走了一步，是边，则返回1
        if (row < 0 || row >=grid.length || col < 0 || col >= grid[0].length) {
            return 1;
        }
        // 如果走了一步，是海水0， 返回1
        if (grid[row][col] == 0) {
            return 1;
        }
        // 如果走了一步是已经走过的，返回0
        if (grid[row][col] == 2) {
            return 0;
        }
        // 设置访问过的表示为2
        grid[row][col] = 2;
        // 以同样的方法，如果当前节点不是边，不是水，且没有访问过，则继续计算它周围的四个点，返回和
        return dfs(grid, row - 1, col) + dfs(grid, row + 1, col)
                + dfs(grid, row, col - 1) + dfs(grid, row , col + 1);
    }


    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 上下左右

    // 使用广度优先算法，需要一个辅助的队列，存储当前节点的周围的节点，该方式类似与处理了深度优先的隐含栈结构
    public static int islandPerimeter2(int[][] grid) {

        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int row = grid.length;
        int col = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int res = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0 || grid[i][j] == 2) {
                    continue;
                }
                queue.offer(new int[]{i, j});
                grid[i][j] = 2;
                while (!queue.isEmpty()) {
                    int[] locate = queue.poll();
                    int curRow = locate[0];
                    int curCol = locate[1];
                    for (int[] dir: dirs) {
                        int newRow = curRow + dir[0];
                        int newCol = curCol + dir[1];
                        if (newRow >= 0 && newRow < row && newCol >= 0 && newCol < col) {
                            if (grid[newRow][newCol] == 1) {
                                queue.offer(new int[]{newRow, newCol});
                                grid[newRow][newCol] = 2;
                            }
                            if (grid[newRow][newCol] == 0) {
                                res++;
                            }
                        } else {
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        //[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0
        int[][] grid = new int[][]{
                {0,1,0,0}, {1,1,1,0}, {0,1,0,0}, {1,1,0,0}
        };
        int res = islandPerimeter2(grid);
        System.out.println(res);
    }


}
