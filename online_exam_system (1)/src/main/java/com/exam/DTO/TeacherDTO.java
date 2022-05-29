package com.exam.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherDTO implements Serializable {
    private Integer teaId;
    private Integer teaState;
    private String teaName;
    private String colName;
    private String teaPassword;
    private String teaUsername;
    private Integer current;
    private Integer size;
}
