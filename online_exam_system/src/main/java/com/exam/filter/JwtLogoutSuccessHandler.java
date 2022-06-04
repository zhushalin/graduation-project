package com.exam.filter;

import com.exam.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutSuccessHandler{

	@Autowired
	JwtUtils jwtUtils;


}
