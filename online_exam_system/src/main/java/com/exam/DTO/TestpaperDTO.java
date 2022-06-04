package com.exam.DTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestpaperDTO implements Serializable {
    private Integer tpId;
    private Integer subId;
    private Integer stuId;
    private Integer teaId;
    private String tpName;
    private Integer tpType;
    private Integer examTotal;
    private String examPass;
    private String examPassword;
    private Integer current;
    private Integer size;

    private String examTime;
    private String examLength;
    private Integer examState;
}
