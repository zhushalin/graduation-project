package com.exam.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class ScoreVO implements Serializable {
    private Integer id;
    private Integer tpId;
    private Integer subId;
    private Integer stuId;
    //学生姓名
    private String stuName;
    //考试名称
    private String tpName;
    private String subName;
    //离开页面次数
    private int leaveTimes;
    //作答时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    //交卷时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date submitTime;
    private int state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime examTime;
    @JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
    private LocalTime examLength;
    private Integer examLong;
    private Integer examTotal;
    private Integer examScore;
    private String examRes;
    private String files;
    private String[] fileUrls;

    private Integer current;
    private Integer size;

}
