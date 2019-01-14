package com.open.boot.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 通用工具类
 * 
 * @author tys
 *
 */
public class CommonUtil {

	/**
	 * 判断输入的字符串参数是否为空
	 * 
	 * @param input input
	 * @return boolean 空则返回true,非空则flase
	 */
	public static boolean isEmpty(String input) {
		return null == input || 0 == input.length() || 0 == input.replaceAll("\\s", "").length();
	}

	/**
	 * 获取一个字符串的简明效果
	 * 
	 * @param data data
	 * @return String 返回的字符串格式类似于"abcd***hijk"
	 */
	public static String getStringSimple(String data) {
		return data.substring(0, 4) + "***" + data.substring(data.length() - 4);
	}

	// 日期
	/**
	 * 获取前一天日期yyyyMMdd
	 * 
	 * 经测试，针对闰年02月份或跨年等情况，该代码仍有效。测试代码如下 calendar.set(Calendar.YEAR, 2013);
	 * calendar.set(Calendar.MONTH, 0); calendar.set(Calendar.DATE, 1);
	 * 测试时，将其放到<code>calendar.add(Calendar.DATE, -1);</code>前面即可
	 * 
	 * @return 返回的日期格式为yyyyMMdd
	 */
	public static String getYestoday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/**
	 * 获取当前的日期yyyyMMdd
	 * 
	 * @return 获取当前的日期yyyyMMdd
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	/**
	 * 获取当前的时间yyyyMMddHHmmss
	 * 
	 * @return 获取当前的日期yyyyMMdd
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * 通过身份证获取性别
	 *
	 * @param idNumber 身份证号
	 * @return 返回性别, 0 保密 , 1 男 2 女
	 */
	public static Integer getGenderByIdNumber(String idNumber) {

		int gender = 0;

		if (idNumber.length() == 15) {
			gender = Integer.parseInt(String.valueOf(idNumber.charAt(14))) % 2 == 0 ? 2 : 1;
		} else if (idNumber.length() == 18) {
			gender = Integer.parseInt(String.valueOf(idNumber.charAt(16))) % 2 == 0 ? 2 : 1;
		}

		return gender;

	}

	/**
	 * 通过身份证获取生日
	 *
	 * @param idNumber 身份证号
	 * @return 返回生日, 格式为 yyyy-MM-dd 的字符串
	 */
	public static String getBirthdayByIdNumber(String idNumber) {

		String birthday = "";

		if (idNumber.length() == 15) {
			birthday = "19" + idNumber.substring(6, 8) + "-" + idNumber.substring(8, 10) + "-"
					+ idNumber.substring(10, 12);
		} else if (idNumber.length() == 18) {
			birthday = idNumber.substring(6, 10) + "-" + idNumber.substring(10, 12) + "-" + idNumber.substring(12, 14);
		}

		return birthday;

	}

	/**
	 * 生成随机密码
	 *
	 * @param pwd_len 生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 获取map的key所对应的值
	 * 
	 * @param map map
	 * @param key key
	 * @return map的key所对应的值
	 */
	public static String getMapValue(Map<String, Object> map, String key) {
		if (map.containsKey(key)) {
			return map.get(key) == null ? "" : map.get(key).toString();
		} else {
			return "";
		}
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay specifiedDay
	 * @return 指定日期的前一天
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay specifiedDay
	 * @return 指定日期的后一天
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	// 获得当天0点时间
	public static String getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		String data = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(cal.getTime());
		return data;
	}

	// 获得当天24点时间
	public static String getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		String data = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(cal.getTime());
		return data;
	}

	// 判断是邮箱还是手机号的正则表达式
	public static String email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	public static String tel = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
}
