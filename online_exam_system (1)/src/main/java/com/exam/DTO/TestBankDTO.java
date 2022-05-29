package com.exam.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestBankDTO implements Serializable {
    private Long[] id;
    private Integer testId;
    private Long[] ids;
    private Integer tpId;
    private Integer teaId;
    private Integer subId;
    private Integer testType;
    private String testContent;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char[] answer;
    private String stringAnswer;
    private Integer score;
    private Integer current;
    private Integer size;
}
