package com.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *  收藏实体类
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Favorite {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     * */
    private Integer studentId;

    /**
     * 收藏对象类型  1 资料， 2 习题
     */
    private Integer type;

    /**
     * 学习资料id
     */
    private Integer fileId;

    /**
     * 习题id
     */
    private Integer questionId;

}
