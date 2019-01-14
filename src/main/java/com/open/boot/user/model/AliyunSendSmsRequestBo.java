package com.open.boot.user.model;

import com.aliyuncs.http.MethodType;

public class AliyunSendSmsRequestBo {
	private MethodType method;// 请求类型
	private String phoneNumbers;// 待发送手机号
	private String signName = "易勘通";// 短信签名-可在短信控制台中找到
	private String templateCode;// 短信模板-可在短信控制台中找到
	private String templateParam;// 可选:模板中的变量
	private String outId;// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者

	public MethodType getMethod() {
		return method;
	}

	public void setMethod(MethodType method) {
		this.method = method;
	}

	public String getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateParam() {
		return templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	@Override
	public String toString() {
		return "SendSmsRequestBo{" + "method='" + method + '\'' + ", phoneNumbers='" + phoneNumbers + '\''
				+ ", signName='" + signName + '\'' + ", templateCode='" + templateCode + '\'' + ", templateParam='"
				+ templateParam + '\'' + ", outId=" + outId + '}';
	}
}
