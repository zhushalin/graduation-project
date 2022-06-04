package com.exam.DTO;

import com.exam.VO.OnlineExam.TBVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AnswersheetDTO implements Serializable {
    //试卷id
    private Integer tpId;
    //学生id
    private Integer stuId;
    //答题卡集合
    private List<TBVO> answerList;
    //离开页面次数
    private Integer leaveTimes;
    //作答时间
    private String startTime;
    private String atype;
}
