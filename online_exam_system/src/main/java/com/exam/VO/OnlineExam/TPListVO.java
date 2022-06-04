package com.exam.VO.OnlineExam;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TPListVO implements Serializable {
    //单选题集合
    public List<TBVO> radioList;
    //多选题集合
    public List<TBVO> multiList;
    //判断题集合
    public List<TBVO> judgeList;
    //问答题集合
    public List<TBVO> questionList;
    //倒计时(剩余结束秒数)
    public Integer leftSeconds;
    //学生成绩
    public Integer ExamScore;
}
