package com.exam.VO;

import com.exam.entity.Major;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MajorVO extends Major implements Serializable {
    private Integer majorId;
    private String majorName;
    private List<MajorVO> children = new ArrayList<>();
}
