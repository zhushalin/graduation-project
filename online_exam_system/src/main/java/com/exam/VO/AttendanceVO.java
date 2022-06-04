package com.exam.VO;

import com.exam.entity.Attendance;
import lombok.Data;

@Data
public class AttendanceVO extends Attendance {
    //当前页
    private int current;
    //每页数量
    private int size;
}
