package com.open.boot.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.boot.core.Result;
import com.open.boot.core.ResultGenerator;
import com.open.boot.email.EmailUtil;

@RestController
@RequestMapping("/sendEmail")
public class EmailController {
	@Autowired
	EmailUtil emailUtil;

	/**
	 * 发送简单文本邮件
	 * 
	 * @return 操作结果
	 */
	@RequestMapping("/sendMailTxt")
	public Result sendSimpleEmail() {
		emailUtil.sendSimpleEmail();
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 发送Html邮件
	 * 
	 * @return 操作结果
	 */
	@RequestMapping("/sendHtmlEmail")
	public Result sendHtmlEmail() {
		emailUtil.sendHtmlEmail();
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 发送内联资源邮件
	 * 
	 * @return 操作结果
	 */
	@RequestMapping("/sendInlineResourceMail")
	public Result sendInlineResourceMail() {
		emailUtil.sendInlineResourceMail();
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 发送模板邮件
	 * 
	 * @param id 用户id
	 * @return 操作结果
	 */
	@RequestMapping("/sendTemplateMail")
	public Result sendTemplateMail(Integer id) {
		emailUtil.sendTemplateMail(id);
		return ResultGenerator.genSuccessResult();
	}
}
