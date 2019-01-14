package com.open.boot.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.open.boot.common.CommonConstant;
import com.open.boot.core.Result;
import com.open.boot.core.ResultGenerator;
import com.open.boot.core.utils.CommonUtil;

/**
 * 图片管理接口
 * 
 * @author yyc
 *
 */
@Controller
@RequestMapping("/picture")
public class PictureController {
	public static final String BASE_URL = CommonConstant.PICTURE_URL;

	/**
	 * 单图片、文件上传
	 * 
	 * @param request request
	 * @return 保存的路径
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Result upload(HttpServletRequest request) {
		try {
			List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

			if (files.size() < 1) {
				return ResultGenerator.genFailResult("文件为空");
			}
			MultipartFile file = files.get(0);
			String imageType = request.getParameter("imageType");// 图片对应类型
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			String filePath = BASE_URL;
			String relativePath = "";
			int random = new Random().nextInt(10000);
			switch (imageType) {
			case "1":
				break;

			default:
				break;
			}
			// 设置文件存储路径
			String name = CommonUtil.getCurrentTime() + "_" + random + suffixName;
			String path = filePath + name;
			File dest = new File(path);
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();// 新建文件夹
			}
			file.transferTo(dest);// 文件写入

			return ResultGenerator.genSuccessResult(relativePath + name);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultGenerator.genFailResult("上传失败");
	}

	/**
	 * 多图片上传
	 * 
	 * @param request request
	 * @return imageType
	 */
	@RequestMapping(value = "/uploadMore", method = RequestMethod.POST)
	@ResponseBody
	public Result handleFileUpload(HttpServletRequest request) {
		String imageType = request.getParameter("imageType");// 图片对应类型
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			String filePath = BASE_URL;
			// 设置文件存储路径
			switch (imageType) {
			default:
				break;
			}
			// 检测是否存在目录
			File dest = new File(filePath);
			if (!dest.exists()) {
				dest.mkdirs();// 新建文件夹
			}
			if (!file.isEmpty()) {
				try {
					// 获取文件名
					String fileName = file.getOriginalFilename();
					// 获取文件的后缀名
					String suffixName = fileName.substring(fileName.lastIndexOf("."));
					int random = new Random().nextInt(10000);
					String name = CommonUtil.getCurrentTime() + "_" + random + suffixName;
					byte[] bytes = file.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + name)));// 设置文件路径及名字
					stream.write(bytes);// 写入
					stream.close();
				} catch (Exception e) {
					stream = null;
					return ResultGenerator.genFailResult("第 " + i + " 个文件上传失败  ==> " + e.getMessage());
				}
			} else {
				return ResultGenerator.genFailResult("第 " + i + " 个文件上传失败因为文件为空");
			}
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 图片显示相关代码
	 * 
	 * imageUrl 图片对应url
	 * 
	 * @param request  request
	 * @param response response
	 */
	@RequestMapping("/viewPicture")
	public void viewPicture(HttpServletRequest request, HttpServletResponse response) {
		String basePath = BASE_URL;
		String imageUrl = request.getParameter("imageUrl");// 图片对应url
		String fileName = imageUrl.trim().substring(imageUrl.lastIndexOf("\\") + 1); // 文件名
		if (fileName != null) {
			// 设置文件路径
			String realPath = basePath + imageUrl;
			// File file = new File(realPath, fileName);
			File file = new File(realPath);
			if (file.exists()) {
				// response.setContentType("application/force-download");
				// 设置强制下载不打开
				// response.addHeader("Content-Disposition",
				// "attachment;fileName=" + fileName);// 设置文件名 ,取消注释就是下载
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
