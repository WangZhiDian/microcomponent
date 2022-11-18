package com.meng.practice.practice.datastructure;

public class User {

    int age;

    String name;

    int Score;

    public User(String name, int age, int score) {
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
}
