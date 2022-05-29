package com.exam.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Testpaper implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 试卷id
     */
    @TableId(value = "tp_id", type = IdType.AUTO)
    private Integer tpId;
    /**
     * 科目id
     */
    private Integer subId;
    /**
     * 教师id
     */
    private Integer teaId;

    /**
     * 试卷名称
     */
    private String tpName;

    /**
     * 试卷类型
     */
    private Integer tpType;

    /**
     * 考试总分
     */
    private Integer examTotal;

    /**
     * 考试及格线
     */
   /* private String examPass;*/


    private String examPassword;

    /**
     * 考试时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime examTime;
    /**
     * 考试时长
     */
    @JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
    private LocalTime examLength;

    /**
     * 考试状态
     */
    private Integer examState;
    private String files;
}
