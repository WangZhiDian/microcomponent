package com.meng.practice.practice.niuke;

import java.util.*;

public class HJ_41_称砝码 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) { // 注意 while 处理多个 case
            String a = in.nextLine();
            String[] weightArr = in.nextLine().split(" ");
            String[] numArr = in.nextLine().split(" ");
            List<Integer> weightList = new ArrayList<>();
            for(int i = 0; i < numArr.length; i++) {
                int n = Integer.parseInt(numArr[i]);
                int weight = Integer.parseInt(weightArr[i]);
                for (int j = 0; j < n; j++) {
                    weightList.add(weight);
                }
            }
            boolean[] used = new boolean[weightList.size()];
            int[] weightArrP = new int[weightList.size()];
            for (int i = 0; i < weightList.size(); i++) {
                weightArrP[i] = weightList.get(i);
            }

            traceback(weightArrP, used, 0, 0, weightList.size());

            System.out.println(allWeightSet.size());
        }
    }
    private static Set<Integer> allWeightSet = new HashSet<>();
    // index 当前砝码的位置,寻找搜索路径
    private static void traceback(int[] weightList, boolean[] used,
                                  int index, int preWeight, int allLength) {
        // 停止条件: 已经找到了最后一个砝码
        if (index >= allLength) {
            return;
        }

        for (int i = index; i < allLength; i++) {
            if (!used[i]) {
                int allWeight = preWeight + weightList[i];
                allWeightSet.add(allWeight);
                used[i] = true;
                traceback(weightList, used, i + 1, allWeight, allLength);
                used[i] = false;
            }
        }
    }

}
