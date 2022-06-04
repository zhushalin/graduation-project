package com.exam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.VO.LoginVO;
import com.exam.utils.RedisUtil;
import com.exam.utils.WebContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	@Autowired
    HttpServletRequest req;

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 获取页面
	 * @return
	 */
	public Page getPage() {
		int current = ServletRequestUtils.getIntParameter(req, "cuurent", 1);
		int size = ServletRequestUtils.getIntParameter(req, "size", 10);

		return new Page(current, size);
	}


	public LoginVO getLoginUser() {
		return WebContextUtil.getLoginUser();
	}
}
