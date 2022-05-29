package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.DTO.AttendanceDTO;
import com.exam.VO.AttendanceVO;
import com.exam.entity.Attendance;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  学习资料服务类
 * </p>
 *
 * @author
 * @since
 */
public interface AttendanceService extends IService<Attendance> {

    /**
     * 根据 map条件查询所有
     * @param
     * @return
     */
    List<AttendanceVO> getAll(AttendanceDTO attendanceDTO);

    /**
     *添加
     * @param attendance
     * @return
     */
    int add(Attendance attendance);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据课程id删除
     * @param subjectId
     * @return
     */
    int deleteBySubjectId(int subjectId);

    /**
     * 更新
     * @param attendance
     * @return
     */
    int update(Attendance attendance);
}
