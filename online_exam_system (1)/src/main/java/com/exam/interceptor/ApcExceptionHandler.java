package com.exam.interceptor;


import com.exam.common.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApcExceptionHandler {
    


    //拦截500给前端
    @ExceptionHandler(value = Exception.class)
    Object handleMyException(HttpServletRequest request, Exception e) {
        return ResultUtil.error(500, e.getMessage());

    }
}
