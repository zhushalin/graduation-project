package com.exam.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *  学习资料DTO类
 * </p>
 *
 * @author
 * @since
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningFileDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 课程id
     * */
    private Integer subjectId;

    /**
     * 文件名
     */
    private String name;
    private String type;
    private String titile;
    private String content;
    private String fileurl;

    private Integer current;
    private Integer size;
}
