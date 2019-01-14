package com.open.boot.core;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
	private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
	private static final String DEFAULT_FAIL_MESSAGE = "fail";
	public static Result genSuccessResult() {
		return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
	}

	public static Result genSuccessResult(Object data) {
		return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
	}

	public static Result genFailResult(Object data) {
		return new Result().setCode(ResultCode.FAIL).setMessage(DEFAULT_FAIL_MESSAGE).setData(data);
	}
	
	public static Result genFailResult(String message) {
		return new Result().setCode(ResultCode.FAIL).setMessage(message);
	}
}
