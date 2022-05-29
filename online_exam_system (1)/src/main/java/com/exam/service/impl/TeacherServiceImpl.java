package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.LoginDTO;
import com.exam.DTO.TeacherDTO;
import com.exam.VO.LoginVO;
import com.exam.VO.TeacherVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;

import com.exam.entity.Teacher;
import com.exam.mapper.TeacherMapper;
import com.exam.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  教师实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    /**
     * 教师用户登录
     * @param loginDTO
     * @return
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        LoginVO loginVO = new LoginVO();
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("tea_username",loginDTO.getUserName())
                .eq("tea_password",loginDTO.getPassword());
        Teacher user = baseMapper.selectOne(wrapper);
        if (user!=null&&(loginDTO.getPassword().equals(user.getTeaPassword()))){
            loginVO.setUserName(loginDTO.getUserName());
            loginVO.setName(user.getTeaName());
            loginVO.setId(user.getTeaId());
            loginVO.setUserState(user.getTeaState());
            loginVO.setRole(loginDTO.getRole());
            return loginVO;
        }
        return null;
    }

    /**
     * 查询所有教师
     * @return
     */
    public List<TeacherVO> getTeacherlist(TeacherDTO teacherDTO){
        QueryWrapper<TeacherVO> wrapper = new QueryWrapper<>();
        //编辑或搜索时根据获取的id搜索
        if (teacherDTO!=null){
            if (teacherDTO.getColName()!=null){
                wrapper.like("col_name",teacherDTO.getColName());
            }
            if (teacherDTO.getTeaId()!=null){
                wrapper.like("tea_id",teacherDTO.getTeaId());
            }
            if (teacherDTO.getTeaName()!=null){
                wrapper.like("tea_name",teacherDTO.getTeaName());
            }
            if (teacherDTO.getTeaState()!=null){
                wrapper.like("tea_state",teacherDTO.getTeaState());
            }
        }
        List<TeacherVO> teacherlist = baseMapper.getTeacherlist(wrapper);
        return teacherlist;
    }

    /**
     * 根据教师id查询
     * @param id
     * @return
     */
    public Msg getTeacherById(int id){
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("tea_id",id);
        List<Teacher> teachers = baseMapper.selectList(wrapper);
        if(teachers.size()>=0){
            return ResultUtil.success(teachers);
        }else{
            return ResultUtil.error(400,"该教师id不存在");
        }
    }

    /**
     *添加教师
     * @param teacher
     * @return
     */
    public int saveTeacher(Teacher teacher){
        getTeacher(teacher);
        return baseMapper.insert(teacher);
    }

    /**
     * 删除教师
     * @param id
     * @return
     */
    public Msg delTeacher(int id){
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("tea_id",id);
        int del = baseMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"教师用户删除失败");
        }
    }

    /**
     * 教师信息更新
     * @param teacher
     * @return
     */
    public int updateTeacher(Teacher teacher){
//        getTeacher(teacher);
//        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
//        wrapper.eq("tea_id",teacher.getTeaId());
        return baseMapper.updateById(teacher);
    }

    public Teacher getTeacher(Teacher teacher){
        if (teacher.getTeaPassword()==null|| "".equals(teacher.getTeaPassword().replaceAll("\\s*",""))){
            teacher.setTeaPassword("123456");
        }else{
            teacher.setTeaPassword(teacher.getTeaPassword());
        }
        return teacher;
    }
}
