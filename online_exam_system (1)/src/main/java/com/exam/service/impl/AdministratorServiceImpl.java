package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.DTO.LoginDTO;
import com.exam.VO.LoginVO;
import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.Administrator;
import com.exam.mapper.AdministratorMapper;
import com.exam.service.AdministratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  管理员实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {
    @Autowired
    AdministratorMapper baseMapper;
    /**
     * 管理员用户登录
     * @param loginDTO
     * @return
     */
    public LoginVO login(LoginDTO loginDTO){
        LoginVO loginVO = new LoginVO();
        QueryWrapper<Administrator> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_username",loginDTO.getUserName())
                .eq("admin_password",loginDTO.getPassword());
        Administrator user = baseMapper.selectOne(wrapper);
        if (user!=null&&(loginDTO.getPassword().equals(user.getAdminPassword()))){
            loginVO.setUserName(loginDTO.getUserName());
            loginVO.setId(user.getAdminId());
            loginVO.setName(user.getAdminName());
            loginVO.setRole(loginDTO.getRole());
            return loginVO;
        }
        return null;
    }

    /**
     * 查询所有管理员
     * @return
     */
    public Msg getAdministrator(){
        List<Administrator> admins = baseMapper.selectList(null);
        if (admins.size()>=0){
            return ResultUtil.success(admins);
        }else{
            return ResultUtil.error(400,"没有管理员用户存在");
        }
    }

    /**
     * 根据管理员id查询
     * @param id
     * @return
     */
    public Msg getAdministratorById(int id){
        QueryWrapper<Administrator> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id",id);
        List<Administrator> admins = baseMapper.selectList(wrapper);
        if(admins.size()>=0){
            return ResultUtil.success(admins);
        }else{
            return ResultUtil.error(400,"该管理员id不存在");
        }
    }

    /**
     *添加管理员
     * @param admin
     * @return
     */
    public Msg addAdministrator(Administrator admin){
        int i = baseMapper.insert(admin);
        if (i>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"管理员添加失败");
        }
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    public Msg delAdministrator(int id){
        QueryWrapper<Administrator> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id",id);
        int del = baseMapper.delete(wrapper);
        if(del>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"管理员用户删除失败");
        }
    }

    /**
     * 管理员信息更新
     * @param admin
     * @return
     */
    public Msg updateAdministrator(Administrator admin){
        int update = baseMapper.updateById(admin);
        if(update>0){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(400,"管理员信息修改失败");
        }
    }
}
