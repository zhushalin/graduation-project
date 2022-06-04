package com.exam.entity;

import java.io.Serializable;
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
public class College implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学院id
     */
    private Integer colId;

    /**
     * 学院名称
     */
    private String colName;


}
