package com.meng.practice.practice.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Test {



    private void testUserList() {
        // 方式1 使用comparator测试排序
        User user01 = new User("wangdian", 10, 10);
        User user02 = new User("wangmeng", 10, 20);
        User user03 = new User("wangdian", 20, 20);
        User user04 = new User("wangmeng", 10, 10);

        List<User> userList = new ArrayList<>();
        userList.add(user01);
        userList.add(user02);
        userList.add(user03);
        userList.add(user04);

        Collections.sort(userList, new ComparatorUser());
        userList.forEach(user -> System.out.println(user.toString()));

        System.out.println();
        // 方式2 使用comparable测试
        List<UserComparable> userComparableList = new ArrayList<>();
        userComparableList.add(new UserComparable("wangdian", 20, 20));
        userComparableList.add(new UserComparable("wangdian", 10, 10));
        userComparableList.add(new UserComparable("wangmeng", 10, 20));
        userComparableList.add(new UserComparable("wangmeng", 10, 10));

        Collections.sort(userComparableList);
        userComparableList.forEach(user -> System.out.println(user.toString()));

        System.out.println();
        // 方式3 比较时直接用lamba方式书写比较器
        List<User> userList2 = new ArrayList<>();
        userList2.add(new User("wangdian", 10, 10));
        userList2.add(new User("wangmeng", 10, 20));
        userList2.add(new User("wangdian", 20, 20));
        userList2.add(new User("wangmeng", 10, 10));

        userList2.sort((user1, user2) -> {
            if (user1.Score != user2.Score) {
                return user1.Score - user2.Score;
            }
            return user1.age - user2.age;
        });
        userList2.forEach(user -> System.out.println(user.toString()));

    }

    public static void main(String[] args) {
        new Test().testUserList();
    }

}
