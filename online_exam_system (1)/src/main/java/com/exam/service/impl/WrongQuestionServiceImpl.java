package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.DTO.WrongQuestionDTO;
import com.exam.VO.WrongQuestionVO;
import com.exam.entity.WrongQuestion;
import com.exam.entity.WrongQuestion;
import com.exam.mapper.WrongQuestionMapper;
import com.exam.mapper.WrongQuestionMapper;
import com.exam.service.WrongQuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  收藏Service接口实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class WrongQuestionServiceImpl extends ServiceImpl<WrongQuestionMapper, WrongQuestion> implements WrongQuestionService {

    @Override
    public List<WrongQuestionVO> getAll(WrongQuestionDTO wrongQuestionDTO) {
        QueryWrapper wrapper = new QueryWrapper<>();
        if (wrongQuestionDTO != null){
            if (wrongQuestionDTO.getStudentId() != null){
                wrapper.eq("student_id", wrongQuestionDTO.getStudentId());
            }
        }
        List<WrongQuestion> list = baseMapper.selectList(wrapper);
        List<WrongQuestionVO> voList = new ArrayList<>();
        for (WrongQuestion wrongQuestion : list){
            voList.add(newVO(wrongQuestion));
        }
        return voList;
    }

    @Override
    public int add(WrongQuestion wrongQuestion) {
        return baseMapper.insert(wrongQuestion);
    }

    @Override
    public int delete(int id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int deleteByStudentId(int studentId) {
        QueryWrapper queryWrapper = new QueryWrapper<WrongQuestion>();
        queryWrapper.eq("student_id", studentId);
        return baseMapper.delete(queryWrapper);
    }

    @Override
    public int update(WrongQuestion wrongQuestion) {
        return baseMapper.updateById(wrongQuestion);
    }
    //转化为VO对象
    private WrongQuestionVO newVO(WrongQuestion wrongQuestion){
        WrongQuestionVO wrongQuestionVO = new WrongQuestionVO();
        wrongQuestionVO.setId(wrongQuestion.getId());
//        wrongQuestionVO.setSubjectId(wrongQuestion.getSubjectId());
//        wrongQuestionVO.setStudentId(wrongQuestion.getStudentId());
//        wrongQuestionVO.setSignInTime(wrongQuestion.getSignInTime());
//        wrongQuestionVO.setState(wrongQuestion.getState());
        return wrongQuestionVO;
    }
}
