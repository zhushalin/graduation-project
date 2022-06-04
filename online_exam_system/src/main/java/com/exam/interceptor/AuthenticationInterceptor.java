package com.exam.interceptor;

import com.exam.VO.LoginVO;
import com.exam.common.ResultUtil;
import com.exam.utils.JwtUtils;
import com.exam.utils.WebContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //如果是方法探测，直接通过
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        //如果方法有JwtIgnore注解，直接通过
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method=handlerMethod.getMethod();

        // 是否需要忽视token校验
        JwtIgnore authorizeOnMethod = handlerMethod.getMethodAnnotation(JwtIgnore.class);
        JwtIgnore annotationOnClass = handlerMethod.getBean().getClass().getAnnotation(JwtIgnore.class);
        boolean needIgnore = annotationOnClass != null || authorizeOnMethod != null;

        if (needIgnore) {
            return true;
        }

        // 从http请求头中取出token
        String token = request.getHeader(JwtUtils.AUTH_HEADER_KEY);
        if (StringUtils.isBlank(token)) {
//            response.sendRedirect(request.getScheme() +"://"+request.getServerPort()+request.getContextPath()+ "login");
            throw new RuntimeException("用户未登录");
//            return true;
        }
        //验证，并获取token内部信息
        LoginVO userToken = new LoginVO();
        userToken.setId(JwtUtils.getUserId(token));
        userToken.setUserName(JwtUtils.getUsername(token));
        userToken.setRole(JwtUtils.getRole(token));
        userToken.setName(JwtUtils.getName(token));
        userToken.setUserState(JwtUtils.getUserStatus(token));
        //将token放入本地缓存
        WebContextUtil.setLoginUser(userToken);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //方法结束后，移除缓存的token
        WebContextUtil.removeLoginUser();
    }
}
