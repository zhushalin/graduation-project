package com.exam.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentDTO implements Serializable {
    private Integer stuId;
    private Integer stuState;
    private Integer colId;
    private String stuName;
    private String colName;
    private Integer majorId;
    private String stuPassword;
    private String stuUsername;
    private Integer current;
    private Integer size;
}
