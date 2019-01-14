package com.open.boot.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
	SUCCESS(200), // 成功
	FAIL(400), // 请求错误
	UNAUTHORIZED(401), // token失败
	NOT_FOUND(404), // 接口不存在
	UNSIGNED(402),//签名错误
	INTERNAL_SERVER_ERROR(500);// 服务器内部错误,系统繁忙

	private final int code;

	ResultCode(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}
}
