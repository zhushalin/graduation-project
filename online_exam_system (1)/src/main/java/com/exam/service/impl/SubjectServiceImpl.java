package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.SubjectDTO;
import com.exam.VO.SubjectVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Major;
import com.exam.entity.Subject;
import com.exam.mapper.MajorMapper;
import com.exam.mapper.SubjectMapper;
import com.exam.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  科目实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    MajorMapper majorMapper;
    /**
     * 查询所有科目
     * @return
     */
    public List<SubjectVO> getSubjectlist(SubjectDTO subjectDTO){
        QueryWrapper<SubjectVO> wrapper = new QueryWrapper<>();
        if (subjectDTO!=null&&subjectDTO.getSubId()!=null){
            wrapper.like("s.sub_id",subjectDTO.getSubId());
        }
        if (subjectDTO!=null&&subjectDTO.getMajorId()!=null){
            wrapper.like("m.major_name",subjectDTO.getMajorId());
        }
        if (subjectDTO!=null&&subjectDTO.getSubName()!=null){
            wrapper.like("s.sub_name",subjectDTO.getSubName());
        }
        List<SubjectVO> Subjectlist = subjectMapper.getSubjectlist(wrapper);
        return Subjectlist;
    }
    public List<Subject> getSubjectlist(){
        return subjectMapper.selectList(null);
    }

    /**
     * 根据科目id查询
     * @param id
     * @return
     */
    public Msg getSubjectByStuId(int id){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("sub_id",id);
        List<Subject> subjects = subjectMapper.selectList(wrapper);
        if(subjects!=null){
            return ResultUtil.success(subjects);
        }else{
            return ResultUtil.error(400,"该科目id不存在");
        }
    }

    /**
     *添加科目
     * @param subjectDTO
     * @return
     */
    public int saveSubjectDTO(SubjectDTO subjectDTO){
        Subject subject = getSubject(subjectDTO);
        System.out.println(subject);
        return subjectMapper.insert(subject);
    }
    //判断该专业是否已存在该科目
    public boolean checkSubName(SubjectDTO subjectDTO){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("sub_name",subjectDTO.getSubName());
        wrapper.eq("major_id",subjectDTO.getMajorId());

        List<Subject> list = subjectMapper.selectList(wrapper);
        System.out.println("=========list==================");
        System.out.println(list);
        System.out.println("=========list==================");
        if (list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 科目信息更新
     * @param subjectDTO
     * @return
     */
    public int updateSubject(SubjectDTO subjectDTO){
        Subject subject = getSubject(subjectDTO);
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("sub_id",subjectDTO.getSubId());
        return subjectMapper.update(subject, wrapper);
    }

    //获得Subject对象
    public Subject getSubject(SubjectDTO subjectDTO){
        Subject subject = new Subject();
        /*根据专业名称查询专业id*/
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        wrapper.eq("major_id",subjectDTO.getMajorId());
        Major major = majorMapper.selectOne(wrapper);

        subject.setMajorId(major.getMajorId());
        subject.setSubName(subjectDTO.getSubName());
        return subject;
    }
}
