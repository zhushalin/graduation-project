package com.exam.entity;

import java.io.Serializable;

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
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId
    private Integer adminId;

    private String adminUsername;

    private String adminPassword;

    private String adminName;

    private String sex;
    private Integer age;
    private String phone;
    private String email;
}
