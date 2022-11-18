package com.meng.practice.practice.datastructure;

import java.util.Comparator;

public class ComparatorUser implements Comparator<User> {


    @Override
    public int compare(User o1, User o2) {

        if (o1.Score != o2.Score) {
            return o1.Score - o2.Score;
        }
        if (o1.age != o2.age) {
            return o1.age - o2.age;
        }
        return o1.name.compareTo(o2.name);
    }
}
