package com.exam.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TestpaperVO implements Serializable {
    private Integer tpId;
    private Integer subId;
    private Integer teaId;
    private String tpName;
    private String subName;
    private Integer tpType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime examTime;
    @JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
    private LocalTime examLength;
    private Integer examLong;
    private Integer examTotal;
    private Integer examScore;
    private String examPass;
    private String examPassword;
    private Integer examState;
    private Integer current;
    private Integer size;
    private String files;
    //文件路径数组
    public String[] fileUrls;
}
