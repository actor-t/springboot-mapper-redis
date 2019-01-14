package com.open.boot.user.service;

import com.open.boot.user.model.AliyunSendSmsRequestBo;

public interface IAliyunDysmsService {
	// 设置超时时间-可自行调整
	// 初始化ascClient需要的几个参数
	public static final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	public static final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
	// 替换成你的AK
	public static final String accessKeyId = "LTAIUnqBcu2BERl0";// 你的accessKeyId,参考本文档步骤2

	public static final String accessKeySecret = "ipyOp865IJjcIMOH8wmPJCyTVY0xmh";// 你的accessKeySecret，参考本文档步骤2

	// 发送短信
	Boolean sendSms(AliyunSendSmsRequestBo aliyunSendSmsRequestBo);

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 *            电话号码
	 * @param Vcode
	 *            验证码
	 * @param outId 外部流水扩展字段
	 * @param type 0为注册1为修改密码
	 * @return true or false
	 */
	Boolean sendVcodeSms(String phone, String Vcode, String outId, Integer type);

	// 发送提示消息
	Boolean sendNoticeSms(String phone, String templateCode, String templateParam, String outId);

}
