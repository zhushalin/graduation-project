package com.exam.controller;


import com.exam.DTO.LoginDTO;
import com.exam.VO.LoginVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Administrator;
import com.exam.entity.Student;
import com.exam.entity.Teacher;
import com.exam.service.AdministratorService;
import com.exam.service.StudentService;
import com.exam.service.TeacherService;
import com.exam.utils.JwtUtils;
import com.exam.utils.MenuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    AdministratorService administratorService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @RequestMapping("/login")
    public Msg login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        LoginVO loginVO;
        System.out.println("loginDTO:" + loginDTO);

        if (loginDTO.getRole().equals("admin")) {
            loginVO = administratorService.login(loginDTO);
        } else if (loginDTO.getRole().equals("teacher")) {
            loginVO = teacherService.login(loginDTO);

        } else if (loginDTO.getRole().equals("student")) {
            loginVO = studentService.login(loginDTO);
        } else {
            return ResultUtil.error(400,"用户类型异常");
        }
        if (loginVO != null) {
            if (loginVO.getUserState()!=null&&loginVO.getUserState()==0){
                return ResultUtil.error(400,"该用户待审核中，暂时不可使用");
            }
            session.setAttribute("loginVo", loginVO);
            //生成jwt
            String token = JwtUtils.sign(loginVO.getUserName(), loginVO.getRole(), loginVO.getName());

            if (token != null) {
                loginVO.setToken(token);
                System.out.println(ResultUtil.success(loginVO));
                return ResultUtil.success(loginVO);
            }
            return ResultUtil.error(400,"认证获取失败！");
        }
        return ResultUtil.error(400,"用户信息错误，找不到该用户！");
    }


    /**
     * 获取动态菜单栏
     */
    @GetMapping("/sys/menu/nav/{token}")
    public Msg Menu(@PathVariable("token") String token) {
        if (JwtUtils.verify(token)) {
            String role = JwtUtils.getRole(token);
            MenuUtils.CommonMenu();
            if (role.equals("admin")) {
                return ResultUtil.success(MenuUtils.AdminMenu());
            } else if (role.equals("teacher")) {
                return ResultUtil.success(MenuUtils.TeacherMenu());
            } else if (role.equals("student")) {
                return ResultUtil.success(MenuUtils.StudentMenu());
            }
            return ResultUtil.error(400,"用户类型异常");
        }
        return ResultUtil.error(400,"认证已失效,请自行退出重新登录");
    }

}
