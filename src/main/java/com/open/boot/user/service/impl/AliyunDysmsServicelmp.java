package com.open.boot.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.open.boot.user.model.AliyunDysmsTemplateEnum;
import com.open.boot.user.model.AliyunSendSmsRequestBo;
import com.open.boot.user.service.IAliyunDysmsService;

@Service
public class AliyunDysmsServicelmp implements IAliyunDysmsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Boolean sendSms(AliyunSendSmsRequestBo aliyunSendSmsRequestBo) {
		// 初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(aliyunSendSmsRequestBo.getPhoneNumbers());
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(aliyunSendSmsRequestBo.getSignName());
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(aliyunSendSmsRequestBo.getTemplateCode());
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam(aliyunSendSmsRequestBo.getTemplateParam());
		// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId(aliyunSendSmsRequestBo.getOutId());
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = null;
		try {
			logger.error("发送短信,参数:{}", JSON.toJSONString(request));
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ClientException e) {
			logger.error("发送短信异常,参数:{}", JSON.toJSONString(request), e);
			e.printStackTrace();
		}
		logger.error("发送短信返回,发送结果:{}", JSON.toJSONString(sendSmsResponse));
		if (null != sendSmsResponse && null != sendSmsResponse.getCode() && sendSmsResponse.getCode().equals("OK")) {
			// 请求成功
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 发送验证码
	 * 
	 * @param phone 电话号码
	 * @param Vcode 验证码
	 * @param outId 业务方扩展字段
	 * @param type  type
	 * @return 发送状态
	 */
	@Override
	public Boolean sendVcodeSms(String phone, String Vcode, String outId, Integer type) {
		AliyunSendSmsRequestBo aliyunSendSmsRequestBo = new AliyunSendSmsRequestBo();
		aliyunSendSmsRequestBo.setPhoneNumbers(phone);
		aliyunSendSmsRequestBo.setTemplateCode(dysmsTemplateEnumByType(type).getTemplateCode());
		aliyunSendSmsRequestBo.setTemplateParam("{\"code\":" + Vcode + "}");
		aliyunSendSmsRequestBo.setOutId(outId);
		return sendSms(aliyunSendSmsRequestBo);
	}

	/**
	 * 发送提示消息
	 * 
	 * @param phone         phone
	 * @param templateCode  templateCode
	 * @param templateParam templateParam
	 * @param outId         outId
	 * @return 发送状态
	 */
	@Override
	public Boolean sendNoticeSms(String phone, String templateCode, String templateParam, String outId) {
		AliyunSendSmsRequestBo aliyunSendSmsRequestBo;
		aliyunSendSmsRequestBo = new AliyunSendSmsRequestBo();
		aliyunSendSmsRequestBo.setPhoneNumbers(phone);
		aliyunSendSmsRequestBo.setTemplateCode(templateCode);
		aliyunSendSmsRequestBo.setTemplateParam(templateParam);
		aliyunSendSmsRequestBo.setOutId(outId);
		return sendSms(aliyunSendSmsRequestBo);
	}

	/**
	 * 根据验证码类型获取验证模板
	 * 
	 * @param type type
	 * @return 验证模板
	 */
	private AliyunDysmsTemplateEnum dysmsTemplateEnumByType(Integer type) {
		switch (type) {
		case 0:
			return AliyunDysmsTemplateEnum.REGISTER_VCODE;
		case 1:
			return AliyunDysmsTemplateEnum.CHANGE_PASSWORD_VCODE;
		default:
			return null;
		}
	}
}
