package com.eagle.entity;

import java.io.Serializable;

/**
 * @author qinlinsen
 */
public class Grade implements Serializable {
    /**
     * 语文学科分数
     */
    private double chineseScore;
    /**
     * 数学学科分数
     */
    private double mathScore;

    public double getChineseScore() {
        return chineseScore;
    }

    public void setChineseScore(double chineseScore) {
        this.chineseScore = chineseScore;
    }

    public double getMathScore() {
        return mathScore;
    }

    public void setMathScore(double mathScore) {
        this.mathScore = mathScore;
    }
}
