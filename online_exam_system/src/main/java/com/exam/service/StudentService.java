package com.exam.service;

import com.exam.DTO.LoginDTO;
import com.exam.DTO.StudentDTO;
import com.exam.VO.LoginVO;
import com.exam.VO.StudentVO;
import com.exam.common.Msg;
import com.exam.entity.Student;
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
public interface StudentService extends IService<Student> {
    /**
     * 学生用户登录
     * @param loginDTO
     * @return
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 查询所有学生
     * @return
     */
    List<StudentVO> getStudentlist(StudentDTO studentDTO);

    /**
     * 根据学生id查询
     * @param id
     * @return
     */
    Msg getStudentById(int id);

    /**
     *添加学生
     * @param student
     * @return
     */
    int saveStudent(Student student);

    /**
     * 删除学生
     * @param id
     * @return
     */
   /* Msg delStudent(int id);*/

    /**
     * 学生信息更新
     * @param student
     * @return
     */
    int updateStudent(Student student);

}
