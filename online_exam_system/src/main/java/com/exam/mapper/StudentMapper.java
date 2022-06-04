package com.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.exam.VO.StudentVO;
import com.exam.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since
 */
@Component
public interface StudentMapper extends BaseMapper<Student> {
    List<StudentVO> getStudentlist(@Param(Constants.WRAPPER) Wrapper wrapper);
}
