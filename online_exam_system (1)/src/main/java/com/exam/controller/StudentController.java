package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.StudentDTO;
import com.exam.VO.LoginVO;
import com.exam.VO.StudentVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Student;
import com.exam.mapper.StudentMapper;
import com.exam.service.AnswersheetService;
import com.exam.service.StudentService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 学生
 * @author
 * @since
 */
@RestController
@RequestMapping("/Student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    AnswersheetService answersheetService;
    //查询所有学生
    @RequestMapping("/getStudent")
    public Msg getStudent(@RequestBody StudentDTO studentDTO){
        System.out.println(studentDTO);
        List<StudentVO> studentlist = studentService.getStudentlist(studentDTO);
        if (studentlist.size()>0){
            Page page = new Page(studentDTO.getSize(), studentDTO.getCurrent());
            page.build(studentlist);
            return ResultUtil.success(page);
        }
        return ResultUtil.error(400,"抱歉，没有找到学生信息");
    }

    //根据学生id查询
    @RequestMapping("/getById/{stuId}")
    public Msg getStudentById(@PathVariable(name = "stuId") Integer stuId){
//        StudentDTO studentDTO = new StudentDTO();
//        if (stuId!=null){
//            studentDTO.setStuId(stuId);
//        }
//        List<StudentVO> studentlist = studentService.getStudentlist(studentDTO);
//        if (studentlist.size()>0){
//            return ResultUtil.success(studentlist);
//        }
//        return ResultUtil.error(400,"抱歉，所要查询的数据不存在");
        return  studentService.getStudentById(stuId);
    }
    //添加/注册学生
    @RequestMapping({"/save","register"})
    public Msg addStudent(@RequestBody Student student){
        System.out.println("!!!!!!添加!!!!!!!");
        System.out.println(student);
        System.out.println("!!!!!!添加!!!!!!!!");
        if (student!=null){
            int result = studentService.saveStudent(student);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    //修改学生信息
    @RequestMapping("/update")
    public Msg updateStudent(@RequestBody Student student){
        if (student!=null){
            System.out.println(student);
            int i = studentService.updateStudent(student);
            if (i > 0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }

    //修改密码
    @RequestMapping("/updatePassword")
    public Msg updatePass(@RequestBody Map<String, Object> params, HttpSession session){
//        String id = params.get("id").toString();
        String userName = params.get("userName").toString();
        String newPsw = params.get("currentPass").toString();
        String newPsw2 = params.get("checkPass").toString();
        String oldPsw = params.get("password").toString();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_username",userName);
        Student student = studentService.getOne(queryWrapper);
        if (student != null){
            if (!oldPsw.equals(student.getStuPassword())){
                return ResultUtil.error(400,"原始密码输入错误");
            }
            if (!newPsw.equals(newPsw2)){
                return ResultUtil.error(400,"新密码两次输入不一致");
            }
            if(oldPsw.equals(newPsw2)){
                return ResultUtil.error(400,"新密码与旧密码不能一致");
            }
            student.setStuPassword(newPsw);
            int result = studentService.updateStudent(student);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"不能获取当前登录的用户，请重新登录后重试");
    }
    //删除学生
    @RequestMapping("/delete")
    public Msg delStudent(@RequestBody Long[] stuIds){
        System.out.println("======删除====");
        System.out.println(Arrays.asList(stuIds));
        System.out.println("======删除====");
        try {
            studentService.removeByIds(Arrays.asList(stuIds));
        }catch (Exception e){
            return ResultUtil.error(500,"该学生存在关联，无法删除");
        }
        return ResultUtil.success();
    }

}
