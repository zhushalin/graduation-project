package com.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  签到信息实体类
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Attendance {

    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 课程id
     * */
    private Integer subjectId;

    /**
      学生id
     */
    private Integer studentId;

    /**
     * 签到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date signInTime;
    /**
     * 签到状态 0未签到，1已签，2教师代签
     * */
    private int state;
}
