package com.zzg.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
	// 过期时间 24 小时
	private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;

	// 密钥
	private static final String SECRET = "SHIRO+JWT";

	/**
	 * 登录时通过 loginController 生成 token, 5min后过期
	 *
	 * @param username
	 *            用户名
	 * @return 加密的token
	 */
	public static String createToken(String username) {

		try {
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			System.out.println(">algorithm " + algorithm);

			// 返回 token
			// 附带username信息
			return JWT.create().withClaim("username", username)
					// 到期时间
					.withExpiresAt(date)
					// 创建一个新的JWT，并使用给定的算法进行标记
					.sign(algorithm);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 校验 token 是否正确
	 *
	 * @param token
	 *            密钥
	 * @param username
	 *            用户名
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username) {
		System.out.println(">执行 verify");

		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			// 在token中附带了 username 信息
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			// 验证 token
			verifier.verify(token);
			return true;
		} catch (Exception exception) {
			// 出错就返回 false
			return false;
		}
	}

	/**
	 * 根据token 获取 username 获得token中的信息，无需 secret 解密也能获得
	 *
	 * @return token 中包含的 username
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
}
