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
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id
     */
    @TableId(type = IdType.AUTO)
    private Integer stuId;

    private Integer stuState;
    //学院id
    private Integer colId;

    private String stuUsername;

    private String stuPassword;

    private String stuName;
    /**
     * 专业id
     */
    private Integer majorId;

    private String sex;
    private Integer age;
    private String phone;
    private String email;
}
