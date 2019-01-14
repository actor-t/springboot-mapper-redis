package com.open.boot.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FileManageUtil {

	public static String fileMg(MultipartFile file, String filePath) {
		try {
			if (file.isEmpty()) {
				return ("文件为空");
			}
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 设置文件名(防止重名)
			int random = new Random().nextInt(10000);
			String name = CommonUtil.getCurrentTime() + "_" + random + suffixName;
			// 设置文件存储路径
			String path = filePath + name;
			File dest = new File(path);
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();// 新建文件夹
			}
			file.transferTo(dest);// 文件写入
			return name;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 当文件名称相同时把之前的文件剪贴到其它文件夹
	 * 
	 * @param oldFile oldFile
	 * @param newFile newFile
	 */
	public static void cutFile(File oldFile, File newFile) {
		// 检测是否存在目录
		if (!oldFile.getParentFile().exists()) {
			oldFile.getParentFile().mkdirs();// 新建文件夹
		}
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();// 新建文件夹
		}
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		byte[] bytes = new byte[1024];
		int temp = 0;
		try {
			inputStream = new FileInputStream(oldFile);
			fileOutputStream = new FileOutputStream(newFile);
			while ((temp = inputStream.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, temp);
				fileOutputStream.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	// 读取json文件中指定key的内容
	public static String readJsonData(String filePath) throws IOException {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		// 读取文件
		FileReader reader = new FileReader(filePath);// 定义一个fileReader对象，用来初始化BufferedReader
		BufferedReader bReader = new BufferedReader(reader);// new一个BufferedReader对象，将文件内容读取到缓存
		StringBuilder sb = new StringBuilder();// 定义一个字符串缓存，将字符串存放缓存中
		String s = "";
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
		}
		reader.close();
		bReader.close();
		String str = sb.toString();
		// 将读取的数据转换为JSONObject
		JSONObject jsonObject = JSONObject.parseObject(str);
		String data = null;// 定义一个全局变量，保存key对应的value
		if (jsonObject != null) {
			// 取出指定key的数据
			data = jsonObject.getString("content");
		}
		return data;
	}

	public static String writeJsonData(String filePath, String message) throws IOException {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(filePath);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		Map<String, String> map = new HashMap<>();
		map.put("content", message);
		String jsonObject = JSON.toJSONString(map);
		bw.write(jsonObject);
		bw.close();
		fileWriter.close();
		return message;
	}
}
