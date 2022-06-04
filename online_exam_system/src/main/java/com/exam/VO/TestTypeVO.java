package com.exam.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestTypeVO implements Serializable {
    private Integer radioCount;
    private Integer multiCount;
    private Integer judgeCount;
    private Integer scoreTotal;
}
