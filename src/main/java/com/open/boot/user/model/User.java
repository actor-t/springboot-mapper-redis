package com.open.boot.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 用户名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 真实姓名
	 */
	@Column(name = "real_name")
	private String realName;

	/**
	 * 微信用户名
	 */
	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 角色类型
	 */
	private String role;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话
	 */
	private String tel;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 头像地址
	 */
	private String photourl;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 用户类型（1企业2个人）
	 */
	@Column(name = "user_type")
	private String userType;

	/**
	 * 编辑时间
	 */
	@Column(name = "edit_date")
	private Date editDate;

	/**
	 * 所在地-行政区划字典表
	 */
	@Column(name = "area_code")
	private String areaCode;

	/**
	 * 人员证件类型字典表
	 */
	@Column(name = "id_card_type")
	private String idCardType;

	@Column(name = "id_card_number")
	private String idCardNumber;
	/**
	 * 登录token
	 */
	@Transient
	private String token;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 *
	 * @return user_name - 用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名
	 *
	 * @param userName 用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取真实姓名
	 *
	 * @return real_name - 真实姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置真实姓名
	 *
	 * @param realName 真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取微信用户名
	 *
	 * @return nick_name - 微信用户名
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 设置微信用户名
	 *
	 * @param nickName 微信用户名
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 获取密码
	 *
	 * @return password - 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取性别
	 *
	 * @return sex - 性别
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置性别
	 *
	 * @param sex 性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取角色类型
	 *
	 * @return role - 角色类型
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 设置角色类型
	 *
	 * @param role 角色类型
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 获取邮箱
	 *
	 * @return email - 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮箱
	 *
	 * @param email 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取电话
	 *
	 * @return tel - 电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置电话
	 *
	 * @param tel 电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 获取地址
	 *
	 * @return address - 地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 *
	 * @param address 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取头像地址
	 *
	 * @return photourl - 头像地址
	 */
	public String getPhotourl() {
		return photourl;
	}

	/**
	 * 设置头像地址
	 *
	 * @param photourl 头像地址
	 */
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	/**
	 * 获取创建时间
	 *
	 * @return create_date - 创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createDate 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取用户类型（1企业2个人）
	 *
	 * @return user_type - 用户类型（1企业2个人）
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 设置用户类型（1企业2个人）
	 *
	 * @param userType 用户类型（1企业2个人）
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 获取编辑时间
	 *
	 * @return edit_date - 编辑时间
	 */
	public Date getEditDate() {
		return editDate;
	}

	/**
	 * 设置编辑时间
	 *
	 * @param editDate 编辑时间
	 */
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	/**
	 * 获取所在地-行政区划字典表
	 *
	 * @return area_code - 所在地-行政区划字典表
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 设置所在地-行政区划字典表
	 *
	 * @param areaCode 所在地-行政区划字典表
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 获取人员证件类型字典表
	 *
	 * @return id_card_type - 人员证件类型字典表
	 */
	public String getIdCardType() {
		return idCardType;
	}

	/**
	 * 设置人员证件类型字典表
	 *
	 * @param idCardType 人员证件类型字典表
	 */
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	/**
	 * @return id_card_number
	 */
	public String getIdCardNumber() {
		return idCardNumber;
	}

	/**
	 * @param idCardNumber 身份证号码
	 */
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}