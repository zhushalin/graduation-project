package com.exam.filter;

/*public class CaptchaFilter{

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	public KeyDTO keyDTO;
	public final static String CAPTCHA_KEY = "captcha";
	// 校验验证码逻辑
	public boolean validate(LoginDTO loginDTO) {

		String code = loginDTO.getCode();
		String key = loginDTO.getKey();
		System.out.println("key===>"+key);
		System.out.println("code===>"+code);
		if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
			return false;
		}


		System.out.println(redisUtil.hget(CAPTCHA_KEY, key));
		if (!code.equals(redisUtil.hget(CAPTCHA_KEY, key))) {
			return false;
		}

		// 一次性使用
		*//*redisUtil.hdel(CAPTCHA_KEY, key);*//*
		return true;
	}
}
		*/
