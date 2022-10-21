package com.meng.domain;

import lombok.Data;

@Data
public class Score {

    private Long id;

    private Long courseId;

    private Long userId;

    private int score;

    private User user;

    private Course course;
}
