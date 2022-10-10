package com.meng.designpatten.composite;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Employ {

    String dept;
    int salary;

    List<Employ> list = new ArrayList<>();

    public Employ(String dept, int salary) {
        this.dept = dept;
        this.salary = salary;
    }
}
