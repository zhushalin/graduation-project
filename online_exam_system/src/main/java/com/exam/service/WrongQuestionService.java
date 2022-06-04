package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.DTO.WrongQuestionDTO;
import com.exam.VO.WrongQuestionVO;
import com.exam.entity.WrongQuestion;

import java.util.List;

/**
 * <p>
 *  收藏服务类
 * </p>
 *
 * @author
 * @since
 */
public interface WrongQuestionService extends IService<WrongQuestion> {

    /**
     * 根据条件查询所有
     * @param
     * @return
     */
    List<WrongQuestionVO> getAll(WrongQuestionDTO wrongQuestionDTO);

    /**
     *添加
     * @param wrongQuestion
     * @return
     */
    int add(WrongQuestion wrongQuestion);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据学生id删除
     * @param studentId
     * @return
     */
    int deleteByStudentId(int studentId);

    /**
     * 更新
     * @param wrongQuestion
     * @return
     */
    int update(WrongQuestion wrongQuestion);
}
