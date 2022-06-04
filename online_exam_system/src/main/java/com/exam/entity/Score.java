package com.exam.entity;

import java.io.Serializable;
import java.util.Date;

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
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 学生id
     */
    private Integer stuId;

    /**
     * 科目id
     */
    private Integer subId;

    /**
     * 科目成绩
     */
    private Double subScore;

    /**
     * 试卷id
     */
    private Integer tpId;

    /**
     * 离开页面次数
     */
    private Integer leaveTimes;
    /**
     * 开始答题时间
     */
    private Date startTime;
    /**
     * 交卷时间
     */
    private Date submitTime;
    /**
     * 成绩状态，0未公开，1公开
     */
    private Integer state;


}
