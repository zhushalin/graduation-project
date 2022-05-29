package com.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
public class Testbank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 试题id
     */
    @TableId(value = "test_id", type = IdType.AUTO)
    private Integer testId;

    private Integer teaId;

    /**
     * 学科id
     */
    private Integer subId;

    private Integer testType;

    /**
     * 题目内容
     */
    private String testContent;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    /**
     * 答案
     */
    private String answer;

    /**
     * 分值
     */
    private Integer score;

    private Date testDate;


}
