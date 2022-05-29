package com.exam.service;

import com.exam.DTO.LoginDTO;
import com.exam.VO.LoginVO;
import com.exam.common.Msg;
import com.exam.entity.Administrator;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since
 */
public interface AdministratorService extends IService<Administrator> {
    /**
     * 管理员用户登录
     * @param loginDTO
     * @return
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 查询所有管理员
     * @return
     */
    Msg getAdministrator();

    /**
     * 根据管理员id查询
     * @param id
     * @return
     */
    Msg getAdministratorById(int id);

    /**
     *添加管理员
     * @param admin
     * @return
     */
    Msg addAdministrator(Administrator admin);

    /**
     * 删除管理员
     * @param id
     * @return
     */
    Msg delAdministrator(int id);

    /**
     * 管理员信息更新
     * @param admin
     * @return
     */
    Msg updateAdministrator(Administrator admin);
}
