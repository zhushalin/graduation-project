package com.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.Attendance;
import com.exam.entity.LearningFile;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  签到信息Mapper 接口
 * </p>
 *
 * @author
 * @since
 */

@Component
public interface AttendanceMapper extends BaseMapper<Attendance> {
}
