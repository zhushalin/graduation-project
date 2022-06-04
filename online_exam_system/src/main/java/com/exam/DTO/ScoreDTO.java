package com.exam.DTO;

import lombok.Data;

@Data
public class ScoreDTO {
    private Integer subId;
    private Integer tpId;
    private Integer stuId;
    private String tpName;
    private String examTime;
    private Integer state;

    private Integer current;
    private Integer size;
}
