package com.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.TeacherDTO;
import com.exam.VO.LoginVO;
import com.exam.VO.TeacherVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Student;
import com.exam.entity.Teacher;
import com.exam.mapper.TeacherMapper;
import com.exam.service.TeacherService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *  教师
 * @author
 * @since
 */
@RestController
@RequestMapping("/Teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    TeacherMapper teacherMapper;

    //查询所有教师
    @RequestMapping("/getTeacher")
    public Msg getTeahcer(@RequestBody TeacherDTO teacherDTO){
        System.out.println(teacherDTO);
        List<TeacherVO> teacherlist = teacherService.getTeacherlist(teacherDTO);
        if (teacherlist.size()>0){
            Page page = new Page(teacherDTO.getSize(), teacherDTO.getCurrent());
            page.build(teacherlist);
            return ResultUtil.success(page);
        }
        return ResultUtil.error(400,"抱歉，没有找到教师信息");
    }

    //根据教师id查询
    @RequestMapping("/getById/{teaId}")
    public Msg getTeacherById(@PathVariable(name = "teaId") Integer teaId){
//        Teacher teacher = teacherService.getById(teaId);
//        if (teacher != null){
//            return ResultUtil.success(teacher);
//        }
//        return ResultUtil.error(400,"没有该教师信息");
        return teacherService.getTeacherById(teaId);
    }
    //添加/注册教师
    @RequestMapping({"/save","/register"})
    public Msg addTeahcer(@RequestBody Teacher teacher){
        System.out.println("!!!!!!添加!!!!!!!");
        System.out.println(teacher);
        System.out.println("!!!!!!添加!!!!!!!!");
        if (teacher!=null){
            int result = teacherService.saveTeacher(teacher);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"添加失败，请重试");
            }
        }
        return ResultUtil.error(400,"添加内容为空，添加失败");
    }

    //修改教师信息
    @RequestMapping("/update")
    public Msg updateTeacher(@RequestBody Teacher teacher){
        if (teacher!=null){
            System.out.println(teacher);
            if (teacherService.updateTeacher(teacher) > 0){
                return ResultUtil.success();
            }
            return ResultUtil.error(400,"抱歉，修改失败，请重试");
        }
        return ResultUtil.error(400,"抱歉，修改内容为空，修改失败");
    }
    //修改密码
    @RequestMapping("/updatePassword")
    public Msg updatePass(@RequestBody Map<String, Object> params, HttpSession session){
        String id = params.get("id").toString();
        String newPsw = params.get("newPsw1").toString();
        String newPsw2 = params.get("newPsw2").toString();
        String oldPsw = params.get("oldPsw").toString();
        Teacher teacher = teacherService.getById(id);
        if (teacher != null){
            if (!oldPsw.equals(teacher.getTeaPassword())){
                return ResultUtil.error(400,"原始密码输入错误");
            }
            if (!newPsw.equals(newPsw2)){
                return ResultUtil.error(400,"新密码两次输入不一致");
            }
            if(oldPsw.equals(newPsw2)){
                return ResultUtil.error(400,"新密码与旧密码不能一致");
            }
            teacher.setTeaPassword(newPsw);
            int result = teacherService.updateTeacher(teacher);
            if (result>0){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"不能获取当前登录的用户，请重新登录后重试");
    }

    //删除教师
    @RequestMapping("/delete")
    public Msg delTeacher(@RequestBody Long[] teaIds){
        System.out.println("======删除====");
        System.out.println(Arrays.asList(teaIds));
        System.out.println("======删除====");
        try {
            teacherService.removeByIds(Arrays.asList(teaIds));
        }catch (Exception e){
            return ResultUtil.error(500,"该教师存在关联，无法删除");
        }
        return ResultUtil.success("");
    }
}
