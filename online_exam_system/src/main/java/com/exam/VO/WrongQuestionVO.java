package com.exam.VO;

import com.exam.entity.Favorite;
import com.exam.entity.WrongQuestion;
import lombok.Data;

@Data
public class WrongQuestionVO extends WrongQuestion {
    //当前页
    private int current;
    //每页数量
    private int size;
}
