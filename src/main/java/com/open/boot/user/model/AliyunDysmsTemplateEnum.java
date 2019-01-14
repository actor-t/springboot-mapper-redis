package com.open.boot.user.model;

/**
 * 阿里云短信模板枚举类
 */
public enum AliyunDysmsTemplateEnum {
	/**
	 * 验证码类短信
	 */
	CHANGE_PASSWORD_VCODE("SMS_143755129", "您的动态码为：${code}，您正在进行密码重置操作，如非本人操作，请忽略本短信！"),
	REGISTER_VCODE("SMS_143711705", "验证码${code}，您正进行易勘通系统的注册申请，请妥善保管好您的账号和密码！");

	private String TemplateCode;
	private String TemplateConent;

	AliyunDysmsTemplateEnum(String TemplateCode, String TemplateConent) {
		this.TemplateCode = TemplateCode;
		this.TemplateConent = TemplateConent;

	}

	public String getTemplateCode() {
		return TemplateCode;
	}

	public void setTemplateCode(String templateCode) {
		TemplateCode = templateCode;
	}

	public String getTemplateConent() {
		return TemplateConent;
	}

	public void setTemplateConent(String templateConent) {
		TemplateConent = templateConent;
	}
}
