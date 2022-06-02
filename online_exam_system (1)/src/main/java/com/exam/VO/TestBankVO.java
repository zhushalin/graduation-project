package com.exam.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBankVO implements Serializable{
    private Integer testId;
    private Integer teaId;
    private String testDate;
    private String subName;
    private Integer subId;
    private Integer testType;
    private String testContent;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Integer testState;
    private String answer;
    private Integer score;
    private String stringAnswer;
    private String status;
    private String type;
    private String scid;
    private char[] answerChars;
}
