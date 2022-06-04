package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.LoginDTO;
import com.exam.DTO.StudentDTO;
import com.exam.VO.LoginVO;
import com.exam.VO.StudentVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Student;
import com.exam.mapper.StudentMapper;
import com.exam.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper baseMapper;

    /**
     * 学生用户登录
     * @param loginDTO
     * @return
     */
    public LoginVO login(LoginDTO loginDTO){
        LoginVO loginVO = new LoginVO();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_username",loginDTO.getUserName())
                .eq("stu_password",loginDTO.getPassword());
        Student user = baseMapper.selectOne(wrapper);
        if (user!=null&&(loginDTO.getPassword().equals(user.getStuPassword()))){
            loginVO.setUserName(loginDTO.getUserName());
            loginVO.setName(user.getStuName());
            loginVO.setId(user.getStuId());
            loginVO.setUserState(user.getStuState());
            loginVO.setRole(loginDTO.getRole());
            return loginVO;
        }
        return null;
    }

    /**
     * 查询所有学生
     * @return
     */
    public List<StudentVO> getStudentlist(StudentDTO studentDTO){
        QueryWrapper<StudentVO> wrapper = new QueryWrapper<>();
        if (studentDTO!=null&&studentDTO.getColId()!=null){
            wrapper.like("m.col_id",studentDTO.getColId());
        }
        if (studentDTO!=null&&studentDTO.getMajorId()!=null){
            wrapper.like("s.major_id",studentDTO.getMajorId());
        }
        if (studentDTO!=null&&studentDTO.getStuId()!=null){
            wrapper.like("s.stu_id",studentDTO.getStuId());
        }
        if (studentDTO!=null&&studentDTO.getStuName()!=null){
            wrapper.like("s.stu_name",studentDTO.getStuName());
        }
        return baseMapper.getStudentlist(wrapper);
    }

    /**
     * 根据学生id查询
     * @param id
     * @return
     */
    public Msg getStudentById(int id){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id",id);
        List<Student> students = baseMapper.selectList(wrapper);
        if(students.size()>=0){
            return ResultUtil.success(students);
        }else{
            return ResultUtil.error(400,"该学生id不存在");
        }
    }

    /**
     *添加学生
     * @param student
     * @return
     */
    public int saveStudent(Student student){
        getStudent(student);
        return baseMapper.insert(student);
    }

    /**
     * 删除学生
     * @param id
     * @return
     */
    /*public Msg delStudent(int id){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_id",id);
        int del = baseMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"学生用户删除失败");
        }
    }*/

    /**
     * student
     * @return
     */
    public int updateStudent(Student student){
//        getStudent(student);
//        QueryWrapper<Student> wrapper = new QueryWrapper<>();
//        wrapper.eq("stu_id",student.getStuId());
        return  baseMapper.updateById(student);
    }

    public Student getStudent(Student student){
        if (student.getStuPassword()==null || "".equals(student.getStuPassword().replaceAll("\\s*",""))){
            student.setStuPassword("123456");
        }else{
            student.setStuPassword(student.getStuPassword());
        }
        return student;
    }
}
