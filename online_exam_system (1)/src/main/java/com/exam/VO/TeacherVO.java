package com.exam.VO;

import com.exam.entity.Teacher;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherVO extends Teacher implements Serializable {
    private Integer teaId;
    private Integer teaState;
    private String teaUsername;
    private String teaName;
    private String colName;
    private Integer current;
    private Integer size;
}
