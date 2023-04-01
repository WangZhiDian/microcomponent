package com.meng.practice.practice;

public class Test {
    /*给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
此外，你可以假设该网格的四条边均被水包围。

示例 1：
输入：grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
输出：1
示例 2：

输入：grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
输出：3

提示：
m == grid.length
n == grid[i].length
1 <= m, n <= 300
grid[i][j] 的值为 '0' 或 '1'
*/

    public int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] input = new int[grid.length][grid[0].length];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Character ch = grid[i][j];
                input[i][j] = Integer.parseInt(ch.toString());
            }
        }
        int num = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (input[i][j] == 1) {
                    num++;
                    dfs(input, i, j);
                }
            }

        }
        return num;
    }


    private void dfs(int[][] grid, int row, int col) {
        // 边界，回退
        if (row < 0 || row >= grid.length || col < 0 || col >=grid[0].length) {
            return;
        }
        //扫描到海水0，回退
        if (grid[row][col] == 0) {
            return;
        }
        //访问过了，回退
        if (grid[row][col] == 2) {
            return;
        }
        //  扫描过后，标记2
        if (grid[row][col] == 1) {
            grid[row][col] = 2;
        }

        dfs(grid, row + 1, col);
        dfs(grid, row - 1, col);
        dfs(grid, row, col + 1);
        dfs(grid, row, col - 1);
    }

    public static void main(String[] args) {
/*        grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]

  ['1','1','1','1','0'],
  ['1','1','0','1','0'],
  ['1','1','0','0','0'],
  ['0','0','0','0','0']*/

        char[][] grid = new char[][]{
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','1'},
                {'0','0','1','0','0'}
        };

        int ret = new Test().numIslands(grid);
        System.out.println(ret);

    }


}
