package com.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.exam.VO.ExamInfoVO;
import com.exam.VO.TestpaperVO;
import com.exam.entity.Answersheet;
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
public interface AnswersheetMapper extends BaseMapper<Answersheet> {
    List<TestpaperVO> getScore(@Param(Constants.WRAPPER) Wrapper wrapper);
    List<ExamInfoVO> getExamInfo(int tp_id);
}
