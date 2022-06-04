package com.exam.VO.OnlineExam;


import lombok.Data;

/**
 * 试卷试题内容传递给前端
* */

@Data
public class TBVO {
    //试题id
    private Integer testId;
    //试题类型
    private Integer testType;
    //试题内容
    private String testContent;
    private String answer;
    private String stuAnswer;
    private Boolean isanswer;
    //选项A~D
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    //试题是否已作答
    private Boolean answered;
    //试题顺序
    private Integer testSort;
    //题目分数
    private Integer score;
}
