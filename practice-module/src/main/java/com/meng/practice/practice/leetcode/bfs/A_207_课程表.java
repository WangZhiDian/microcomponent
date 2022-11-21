package com.meng.practice.practice.leetcode.bfs;

import java.lang.management.LockInfo;
import java.lang.reflect.Array;
import java.util.*;

public class A_207_课程表 {
    // https://leetcode.cn/problems/course-schedule/description/

    /*
    * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。

在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。

例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。

示例 1：

输入：numCourses = 2, prerequisites = [[1,0]]
输出：true
解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。

207_课程表

*/

    public static boolean canFinish(int numCourses, int[][] prerequisites) {

        int[] indegress = new int[numCourses];
        Map<Integer, ArrayList<Integer>> prerequiseMap = new HashMap<>();
        for (int[] prerequise: prerequisites) {
            ArrayList<Integer> link = prerequiseMap.get(prerequise[1]);
            if (link == null) {
                link = new ArrayList<>();
            }
            link.add(prerequise[0]);
            prerequiseMap.put(prerequise[1], link);
            indegress[prerequise[0]]++;
        }
        // 记录课程，也是入度为0的数据
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegress.length; i++) {
            if (indegress[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int course = queue.poll();
            // 获取该课程上完后，可以上的其他课程的列表
            List<Integer> prerequeseList = prerequiseMap.get(course);
            if (prerequeseList == null) {
                continue;
            }
            for (int pre: prerequeseList) {
                indegress[pre]--;
                if (indegress[pre] == 0) {
                    queue.offer(pre);
                }
            }
        }
        long learnCourse = Arrays.stream(indegress).filter(t -> t > 0).count();
        return learnCourse == 0;
    }


    // https://leetcode.cn/problems/course-schedule-ii/description/
    // 210 课程表 II
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer> order = new ArrayList<>();
        int[] indegress = new int[numCourses];
        Map<Integer, ArrayList<Integer>> prerequiseMap = new HashMap<>();
        for (int[] prerequise: prerequisites) {
            ArrayList<Integer> link = prerequiseMap.get(prerequise[1]);
            if (link == null) {
                link = new ArrayList<>();
            }
            link.add(prerequise[0]);
            prerequiseMap.put(prerequise[1], link);
            indegress[prerequise[0]]++;
        }
        // 记录课程，也是入度为0的数据
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegress.length; i++) {
            if (indegress[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int course = queue.poll();
            order.add(course);
            // 获取该课程上完后，可以上的其他课程的列表
            List<Integer> prerequeseList = prerequiseMap.get(course);
            if (prerequeseList == null) {
                continue;
            }
            for (int pre: prerequeseList) {
                indegress[pre]--;
                if (indegress[pre] == 0) {
                    queue.offer(pre);
                }
            }
        }

        if (order.size() != numCourses) {
            return new int[0];
        }
        return order.stream().mapToInt(t -> t).toArray();
    }


    public static void main(String[] args) {
        int courseNum = 2;
        int[][] indegress = new int[][]{
                {1, 0}
        };
        boolean ret = canFinish(courseNum, indegress);
        System.out.println(ret);
    }



}
