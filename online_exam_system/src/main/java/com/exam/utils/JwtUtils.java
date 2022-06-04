package com.exam.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.exam.VO.LoginVO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class JwtUtils {
	/**
	 * 过期时间一天，
	 *
	 */
	private static final long EXPIRE_TIME = 168 * 60 * 60 * 1000;

	//定义token返回头部
	public static final String AUTH_HEADER_KEY = "Authorization";


	/**
	 * token私钥
	 */
	private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";

	private static final String LOGIN_NAME = "loginName";
	private static final String USER_ID = "userId";
	private static final String NAME = "name";
	private static final String USER_STATUS = "status";
	private static final String ROLE = "role";

	/**
	 * 校验token是否正确
	 *
	 * @param token 密钥
	 * @return 是否正确
	 */
	public static boolean verify(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	public static String getRole(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(ROLE).asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
	public static String getName(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(NAME).asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(LOGIN_NAME).asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 获取登陆用户ID
	 * @param token
	 * @return
	 */
	public static Integer getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(USER_ID).asInt();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	public static Integer getUserStatus(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(USER_STATUS).asInt();
		} catch (JWTDecodeException e) {
			return null;
		}
	}


	/**
	 * 生成token
	 */
	public static String sign(LoginVO login) {
		//            过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//            私钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
//            设置头部信息
		Map<String, Object> header = new HashMap<>(2);
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		// 附带username，userId信息，生成签名
		return JWT.create()
				.withHeader(header)
				.withClaim(LOGIN_NAME, login.getUserName())
				.withClaim(USER_ID, login.getId())
				.withClaim(NAME, login.getName())
				.withClaim(USER_STATUS, login.getUserState())
				.withClaim(ROLE, login.getRole())
				.withExpiresAt(date)
				.sign(algorithm);
	}

}
