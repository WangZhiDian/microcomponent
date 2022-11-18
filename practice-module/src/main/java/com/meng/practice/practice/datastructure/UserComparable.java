package com.meng.practice.practice.datastructure;


public class UserComparable implements Comparable<UserComparable> {

    int age;

    String name;

    int Score;

    public UserComparable(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.Score = score;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", Score=" + Score +
                '}';
    }

    @Override
    public int compareTo(UserComparable o) {
        if (this.Score != o.Score) {
            return this.Score - o.Score;
        }
        if (this.age != o.age) {
            return this.age - o.age;
        }
        return this.name.compareTo(o.name);
    }
}
