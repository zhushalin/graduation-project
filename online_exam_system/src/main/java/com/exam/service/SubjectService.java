package com.exam.service;

import com.exam.DTO.SubjectDTO;
import com.exam.VO.SubjectVO;
import com.exam.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since
 */
public interface SubjectService extends IService<Subject> {

    List<SubjectVO> getSubjectlist(SubjectDTO subjectDTO);

    List<Subject> getSubjectlist();

    boolean checkSubName(SubjectDTO subjectDTO);

    int saveSubjectDTO(SubjectDTO subjectDTO);

    int updateSubject(SubjectDTO subjectDTO);
}
