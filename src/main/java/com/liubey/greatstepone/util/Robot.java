package com.liubey.greatstepone.util;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 机器人抽象类
 * @author eason_lau
 *
 */
public class Robot{
	private String email;
	private String password;
	private DefaultHttpClient httpClient;
	private boolean isLogin;
	private String captcha;
	private String _rtk;
	
	//3g登陆用
	private String lbskey;
	private String verifykey;
	private String sid;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Email:" + this.email + ":Password:" + this.password;
	}
	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String get_rtk() {
		return _rtk;
	}
	public void set_rtk(String _rtk) {
		this._rtk = _rtk;
	}
	public String getLbskey() {
		return lbskey;
	}
	public void setLbskey(String lbskey) {
		this.lbskey = lbskey;
	}
	public String getVerifykey() {
		return verifykey;
	}
	public void setVerifykey(String verifykey) {
		this.verifykey = verifykey;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
}
