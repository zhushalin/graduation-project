package com.exam.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  签到信息DTO类
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AttendanceDTO {

    /**
     * id
     */
    @TableId
    private Integer id;

    private List<Integer> ids;

    /**
     * 课程id
     * */
    private Integer subjectId;

    /**
      学生id
     */
    private Integer studentId;
    /**
     * 签到状态 0未签到，1已签，2教师代签
     * */
    private int state;
    //是否分页
    private boolean isPage;
    //当前页
    private int current;
    //每页数量
    private int size;
}
