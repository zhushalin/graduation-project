package com.exam.service;

import com.exam.DTO.LoginDTO;
import com.exam.DTO.TeacherDTO;
import com.exam.VO.LoginVO;
import com.exam.VO.TeacherVO;
import com.exam.common.Msg;
import com.exam.entity.Teacher;
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
public interface TeacherService extends IService<Teacher> {
    /**
     * 教师用户登录
     * @param loginDTO
     * @return
     */
    LoginVO login(LoginDTO loginDTO);
    /**
     * 查询所有教师
     * @return
     */
    List<TeacherVO> getTeacherlist(TeacherDTO teacherDTO);

    /**
     * 根据教师id查询
     * @param id
     * @return
     */
    Msg getTeacherById(int id);

    /**
     *添加教师
     * @param teacher
     * @return
     */
    int saveTeacher(Teacher teacher);

    /**
     * 删除教师
     * @param id
     * @return
     */
    Msg delTeacher(int id);

    /**
     * 教师信息更新
     * @param teacher
     * @return
     */
    int updateTeacher(Teacher teacher);
}
