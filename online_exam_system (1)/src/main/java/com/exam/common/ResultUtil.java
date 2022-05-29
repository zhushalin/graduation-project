package com.exam.common;

import java.io.Serializable;

/**
 * 封装结果集
 * */
public class ResultUtil implements Serializable {

    /**
     * 请求成功返回
     * @param object
     * @return
     */
    public static Msg success(Object object){
        Msg msg=new Msg();
        msg.setCode(200);
        msg.setMsg("请求成功");
        msg.setData(object);
        System.out.println(msg);
        return msg;
    }
    public static Msg success(){
        return success(null);
    }

    public static Msg error(Integer code,String resultmsg){
        Msg msg=new Msg();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        System.out.println(msg);
        return msg;
    }

    public static Msg error(Integer code,String resultmsg,String data){
        Msg msg=new Msg();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        System.out.println(msg);
        msg.setData(data);
        return msg;
    }
}
