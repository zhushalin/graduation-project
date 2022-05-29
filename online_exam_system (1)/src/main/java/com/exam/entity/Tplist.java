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
public class Tplist implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 试卷id
     */
    @TableId
    private Integer tpId;

    /**
     * 试题id
     */
    private Integer testId;


}
