package com.open.boot.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.druid.util.StringUtils;
import com.open.boot.common.CommonConstant;
import com.open.boot.core.Result;
import com.open.boot.core.ResultGenerator;

/**
 * 文件管理接口
 * 
 * @author tys
 *
 */
@Controller
@RequestMapping("/file")
public class FileManageController {
	public static final String BASE_URL = CommonConstant.PICTURE_URL;

	/**
	 * 单文件上传返回路径并保存到对应数据库(勘察纲要)
	 * 
	 * @param request request包含fileType,projectId,file fileType
	 *                勘察准备资料:01为勘察平面布置图，02为勘察纲要， 勘察成果资料:03为报告，04为附件，05附图， 06其它
	 * @return 操作状态
	 */
	@RequestMapping(value = "/uploadSingleSave")
	@ResponseBody
	public Result uploadSingleSave(HttpServletRequest request) {
		try {
			List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
			if (files.size() < 1) {
				return ResultGenerator.genFailResult("文件为空");
			}
			MultipartFile file = files.get(0);
			String projectId = request.getParameter("projectId");// 项目Id
			String fileType = request.getParameter("fileType");// 文件类型
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			// String suffixName =
			// fileName.substring(fileName.lastIndexOf("."));
			String filePath = BASE_URL;
			String relativePath = "";
			// int random = new Random().nextInt(10000);
			if (projectId == null || "".equals(projectId)) {
				return ResultGenerator.genFailResult("项目id为空");
			}
			switch (fileType) {
			case "01":
				filePath = BASE_URL + "survey//" + projectId + "//planPicture//"; // 勘察平面布置图
				relativePath = "survey//" + projectId + "//planPicture//";
				break;
			default:
				break;
			}
			// 设置文件存储路径
			// String name = CommonUtil.getCurrentTime() + "_" + random +
			// suffixName;
			String path = filePath + fileName;
			File dest = new File(path);
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();// 新建文件夹
			}
			file.transferTo(dest);// 文件写入

			return ResultGenerator.genSuccessResult(relativePath + fileName);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultGenerator.genFailResult("上传失败");
	}

	/**
	 * 文件下载 filePath文件路徑
	 * 
	 * @param request  request
	 * @param response response
	 */
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String basePath = BASE_URL;
		String filePath = request.getParameter("filePath");
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		System.out.println(filePath);
		String fileName = filePath.trim().substring(filePath.lastIndexOf("\\") + 1); // 文件名
		if (fileName != null) {
			// 设置文件路径
			String realPath = basePath + filePath;
			File file = new File(realPath);
			if (file.exists()) {
				response.setContentType("application/force-download");
				// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
																								// ,取消注释就是下载
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
		} else {

		}
	}

	/*
	 * 模板文件下载
	 * 
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		String basePath = BASE_URL + "template//";
		String fileType = request.getParameter("fileType");// 文件路径
		// 设置文件路径
		String realPath = null;
		String fileName = null;
		if (fileType != null) {
			switch (fileType) {
			case "1":
				fileName = "drillHole.xlsx";
				break;
			default:
				break;
			}
			realPath = basePath + fileName;
			File file = new File(realPath);
			if (file.exists()) {
				response.setContentType("application/force-download");
				// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
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
					System.out.println(buffer.toString());
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
