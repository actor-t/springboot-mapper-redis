package com.open.boot.common;

/**
 * 项目常量
 */
public final class CommonConstant {
	public static final String TOKEN_ID = "token";// token
	public static final String TOKEN_SERECT = "zywkykt";// 秘钥
	public static final String PICTURE_URL = "E://zywk//";
	public static final String Message_FilePath = "E://zywk//wx//message.json"; //微信小程序公告json文件
	
	//微信相关
	public static final String appId = "wx866ad2d0db943906"; //appId 和 secret从微信后台获得
	public static final String secret = "dbbfed9af68281dc3dbb75f68eebf878";
	public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=" + appId + "&secret=" + secret; //获取access_token接口
	public static String access_token = "";
	public static String send_message_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
}
