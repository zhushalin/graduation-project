package com.exam.interceptor;


import com.exam.common.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApcExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApcExceptionHandler.class);


    //拦截500给前端
    @ExceptionHandler(value = Exception.class)
    Object handleMyException(HttpServletRequest request, Exception e) {
        logger.error("[handleMyException] , exception", e);
        return ResultUtil.error(500, e.getMessage());

    }
}
