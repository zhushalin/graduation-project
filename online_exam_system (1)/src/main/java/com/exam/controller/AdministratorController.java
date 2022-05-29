package com.exam.controller;


import com.exam.VO.LoginVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Administrator;
import com.exam.entity.Student;
import com.exam.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 管理员
 */
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;
    //查询所有管理员
    @RequestMapping("/getAdmin")
    public Msg getAdmin(){
        return null;
    }
    //根据管理员id查询
    @RequestMapping("/getById/{id}")
    public Msg getAdminById(@PathVariable(name = "id") int id){
//        Administrator administrator = administratorService.getById(id);
//        if (administrator != null)
//            return ResultUtil.success(administrator);
//        else return ResultUtil.error(400,"找不到该id相应的管理员用户");
        return administratorService.getAdministratorById(id);
    }
    //添加/注册管理员
    @RequestMapping({"/addAdmin","register"})
    public Msg addAdmin(@RequestBody Administrator administrator){
        return null;
    }
    //删除管理员
    @RequestMapping("/delAdmin/{id}")
    public Msg delAdmin(@PathVariable(name = "id") int id){
        return null;
    }
    //修改管理员信息
    @RequestMapping("/update")
    public Msg updateAdmin(@RequestBody Administrator administrator){
        return administratorService.updateAdministrator(administrator);
    }
    //修改密码
    @RequestMapping("/updatePassword")
    public Msg updatePass(@RequestBody Map<String, Object> params){
        String id = params.get("id").toString();
        String newPsw = params.get("newPsw1").toString();
        String newPsw2 = params.get("newPsw2").toString();
        String oldPsw = params.get("oldPsw").toString();
        Administrator admin = administratorService.getById(id);
        if (admin != null){
            if (!oldPsw.equals(admin.getAdminPassword())){
                return ResultUtil.error(400,"原始密码输入错误");
            }
            if (!newPsw.equals(newPsw2)){
                return ResultUtil.error(400,"新密码两次输入不一致");
            }
            admin.setAdminPassword(newPsw);
            if (administratorService.updateById(admin)){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(400,"抱歉，修改失败，请重试");
            }
        }
        return ResultUtil.error(400,"不能获取当前登录的用户，请重新登录后重试");
    }
}
