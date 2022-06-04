package com.exam.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExamInfoVO implements Serializable {
    //试卷编号
    private int tpId;
    private String tpName;
    //题目编号
    private int testId;
    private String testContent;
    //答题总数
    private int total;
    //答对数
    private int correctCount;
    //正确率
    private double accuracy;

}
