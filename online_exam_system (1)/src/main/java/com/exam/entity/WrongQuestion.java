package com.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *  错题实体类
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WrongQuestion {

    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 学生id
     * */
    private Integer studentId;

    /**
     * 习题id
     */
    private String questionId;

}
