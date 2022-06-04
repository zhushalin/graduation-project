package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.DTO.AttendanceDTO;
import com.exam.VO.AttendanceVO;
import com.exam.entity.Attendance;
import com.exam.mapper.AttendanceMapper;
import com.exam.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  签到信息Service接口实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    @Override
    public List<AttendanceVO> getAll(AttendanceDTO attendanceDTO) {
        QueryWrapper wrapper = new QueryWrapper<>();
        if (attendanceDTO != null){
            if (attendanceDTO.getSubjectId() != null){
                wrapper.eq("subject_id", attendanceDTO.getSubjectId());
            }
        }
        List<Attendance> list = baseMapper.selectList(wrapper);
        List<AttendanceVO> voList = new ArrayList<>();
        for (Attendance attendance : list){
            voList.add(newVO(attendance));
        }
        return voList;
    }

    @Override
    public int add(Attendance attendance) {
        return baseMapper.insert(attendance);
    }

    @Override
    public int delete(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int deleteBySubjectId(int subjectId) {
        QueryWrapper queryWrapper = new QueryWrapper<Attendance>();
        queryWrapper.eq("subject_id", subjectId);
        return baseMapper.delete(queryWrapper);
    }

    @Override
    public int update(Attendance attendance) {
        return baseMapper.updateById(attendance);
    }
    //转化为VO对象
    private AttendanceVO newVO(Attendance attendance){
        AttendanceVO attendanceVO = new AttendanceVO();
        attendanceVO.setId(attendance.getId());
        attendanceVO.setSubjectId(attendance.getSubjectId());
        attendanceVO.setStudentId(attendance.getStudentId());
        attendanceVO.setSignInTime(attendance.getSignInTime());
        attendanceVO.setState(attendance.getState());
        return attendanceVO;
    }
}
