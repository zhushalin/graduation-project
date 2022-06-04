package com.exam.utils;

import com.exam.VO.LoginVO;

public class WebContextUtil {

    //本地线程缓存token
    private static ThreadLocal<LoginVO> local = new ThreadLocal<>();

    /**
     * 设置token信息
     * @param content
     */
    public static void setLoginUser(LoginVO content){
        removeLoginUser();
        local.set(content);
    }

    /**
     * 获取token信息
     * @return
     */
    public static LoginVO getLoginUser(){
        return local.get();
    }

    /**
     * 移除token信息
     * @return
     */
    public static void removeLoginUser(){
        if(local.get() != null){
            local.remove();
        }
    }
}
