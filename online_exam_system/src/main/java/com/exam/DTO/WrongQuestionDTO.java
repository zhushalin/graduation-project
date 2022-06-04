package com.exam.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 *  签到信息DTO类
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WrongQuestionDTO {

    /**
     * id
     */
    @TableId
    private Integer id;
    //id列表用于批量删除
    private List<Integer> ids;

    /**
      学生id
     */
    private Integer studentId;

    //是否分页
    private boolean isPage;
    //当前页
    private int current;
    //每页数量
    private int size;
}
