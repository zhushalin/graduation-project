package com.exam.VO;


import com.exam.entity.Major;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CollistVO implements Serializable {
    private Integer colId;
    private String  colName;
    private Integer majorId;
    private String  majorName;
    private List<Major> children = new ArrayList<>();

}
