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
public class FavoriteDTO {

    /**
     * id
     */
    @TableId
    private Integer id;
    //id列表用于批量删除
    private List<Integer> ids;

    /**
     * 学生id
     * */
    private Integer studentId;

    /**
     * 收藏对象类型 1资料，2习题
     * */
    private int type;
    //是否分页
    private boolean isPage;
    //当前页
    private int current;
    //每页数量
    private int size;
}
