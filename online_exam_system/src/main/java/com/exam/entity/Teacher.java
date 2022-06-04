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
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师id
     */
    @TableId(type = IdType.AUTO)
    private Integer teaId;

    private Integer teaState;

    private String teaUsername;

    private String teaPassword;

    private String teaName;
    /**
     * 学院名称
     */
    private String colName;
    private String sex;
    private Integer age;
    private String phone;
    private String email;
}
