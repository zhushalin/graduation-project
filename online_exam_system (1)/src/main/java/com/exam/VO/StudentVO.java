package com.exam.VO;

import com.exam.entity.Student;
import lombok.Data;

import java.io.Serializable;

@Data
public class StudentVO extends Student implements Serializable {
    private Integer StuId;
    private Integer colId;
    private Integer stuState;
    private String StuUsername;
    private String StuName;
    private String colName;
    private String majorName;
    private Integer current;
    private Integer size;
}
