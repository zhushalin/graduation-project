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
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 科目id
     */
    @TableId(type = IdType.AUTO)
    private Integer subId;

    /**
     * 专业id
     */

    private Integer majorId;

    /**
     * 科目名称
     */
    private String subName;


}
