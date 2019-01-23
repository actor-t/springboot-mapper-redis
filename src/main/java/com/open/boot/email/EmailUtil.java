package com.open.boot.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	// 读取配置文件中的参数
	@Value("${spring.mail.username}")
	private String sender;

	private static final String recipient = "xxxxxxxx@qq.com";

	/**
	 * 发送简单文本邮件
	 */
	public void sendSimpleEmail() {
		SimpleMailMessage message = new SimpleMailMessage();
		// 发送者
		message.setFrom(sender);
		// 接收者
		message.setTo(recipient);
		// 邮件主题
		message.setSubject("主题：中冶武汉勘察研究院");
		// 邮件内容
		message.setText("勿回");
		javaMailSender.send(message);
	}

	/**
	 * 发送Html邮件
	 */
	public void sendHtmlEmail() {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sender);
			helper.setTo(recipient);
			helper.setSubject("主题：HTML邮件");
			StringBuffer sb = new StringBuffer();
			sb.append("<h1>大标题-h1</h1>").append("<p style='color:#F00'>红色字</p>")
					.append("<p style='text-align:right'>右对齐</p>");
			helper.setText(sb.toString(), true);
		} catch (MessagingException e) {
			throw new RuntimeException("Messaging  Exception !", e);
		}
		javaMailSender.send(message);
	}

	/**
	 * 发送内联资源邮件
	 */
	public void sendInlineResourceMail() {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sender);
			helper.setTo(recipient);
			helper.setSubject("主题：这是有图片的邮件");
			String imgId = "avatar";
			String content = "<html><body>图片：<img src=\'cid:" + imgId + "\' ></body></html>";
			helper.setText(content, true);
			FileSystemResource res = new FileSystemResource(new File("src/main/resources/static/images/avatar.jpg"));
			helper.addInline(imgId, res);
		} catch (MessagingException e) {
			throw new RuntimeException("Messaging  Exception !", e);
		}
		javaMailSender.send(message);
	}

	/**
	 * 发送模板邮件
	 * 
	 * @param id 用户id
	 */
	public void sendTemplateMail(Integer id) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sender);
			helper.setTo(recipient);
			helper.setSubject("主题：易勘通官方邮件--企业审核");
			Context context = new Context();
			context.setVariable("id", id);
			String emailContent = templateEngine.process("emailTemplate", context);
			helper.setText(emailContent, true);
		} catch (MessagingException e) {
			throw new RuntimeException("Messaging  Exception !", e);
		}
		javaMailSender.send(message);
	}
}
