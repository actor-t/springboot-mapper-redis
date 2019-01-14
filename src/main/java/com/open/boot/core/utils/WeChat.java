package com.open.boot.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.open.boot.common.CommonConstant;

@Component
public class WeChat {

	static String accessTokenUrl = CommonConstant.access_token_url;
	static String send_message_url = CommonConstant.send_message_url;

	/**
	 * 每隔两个小时刷新一次accessToken
	 */
//	@Scheduled(fixedRate = 7200000)
	public static void getAccessToken() {
		HttpURLConnection conn = null;
		BufferedReader dataIn = null;
		try {
			// 把字符串转换为URL请求地址
			URL url = new URL(accessTokenUrl);
			// 打开连接
			conn = (HttpURLConnection) url.openConnection();
			// 连接会话
			conn.connect();
			// 获取输入流
			dataIn = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = dataIn.readLine()) != null) {// 循环读取流
				sb.append(line);
			}
			JSONObject jsonObject = JSONObject.parseObject(sb.toString());
			String accessToken = jsonObject.getString("access_token");
			CommonConstant.access_token = accessToken; // 对accessTOken重新赋值
			CommonConstant.send_message_url = send_message_url.replace("ACCESS_TOKEN", accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 重要且易忽略步骤 (关闭流,切记!)
				if (dataIn != null) {
					dataIn.close();
				}
				// 销毁连接
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
