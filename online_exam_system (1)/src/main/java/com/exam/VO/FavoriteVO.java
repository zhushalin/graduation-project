package com.exam.VO;

import com.exam.entity.Attendance;
import com.exam.entity.Favorite;
import lombok.Data;

@Data
public class FavoriteVO extends Favorite {
    //当前页
    private int current;
    //每页数量
    private int size;
}
