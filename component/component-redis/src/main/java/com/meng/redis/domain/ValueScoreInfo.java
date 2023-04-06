package com.meng.redis.domain;

/**
 * value score info
 *
 * @author : s00441405
 */
public class ValueScoreInfo {

    private String value;

    private Long score;

    /**
     * value score info
     */
    public ValueScoreInfo() {
    }

    /**
     * value score info
     *
     * @param value :
     * @param score :
     */
    public ValueScoreInfo(String value, Long score) {
        this.value = value;
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
