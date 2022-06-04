package com.exam.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Msg<T> implements Serializable {

    /*状态码*/
    private Integer code;

    /*提示信息 */
    private String msg;

    /*具体内容*/
    private  T data;
}
