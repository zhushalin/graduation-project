package com.exam.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Answersheet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 答题卡id
     */
    @TableId(type = IdType.AUTO)
    private Integer asId;

    /**
     * 试题id
     */
    private Integer testId;

    /**
     * 学生答案
     */
    private String stuAnswer;

    /**
     * 考生得分
     */
    private Integer stuScore;

    /**
     * 考生id
     */
    private Integer stuId;

    /**
     * 试卷id
     */
    private Integer tpId;
    /**
     * 是否为问答题
     */
    private Integer isAsk;

    private String atype;

}
