/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: UserInfo.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/19  REV.  备注
 *         2011/06/19  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.domain;

import android.provider.BaseColumns;

/**
 * 
 * 用户信息类。
 * 
 * 
 * <pre>
 * 
 * 2011/06/19, 张荣
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 2011/06/19 曲磊  增加属性字段
 * 2011/06/20 曲磊  增加属性字段和表结构
 * 
 * </pre>
 * 
 * @author 张荣
 * @version 1.00
 */
public class UserInfo {

	public static final class UserInfo_Conlumns implements BaseColumns {

		public static final String TABLE_NAME = "UserInfo";

		public static final String PERSONID = "personId";
		public static final String WATCHID = "watchId";
		public static final String EMAIL = "email";
		public static final String PWD = "pwd";
		public static final String PHONENUM = "phoneNum";
		public static final String USERNAME = "userName";
		public static final String SUMMARYTIMER = "summaryTimer";
		public static final String ARTICLETIMER = "articleTimer";
		public static final String TARGETALERTTIMER = "targetAlertTimer";

		public static final String T_CREATE = "create table " + TABLE_NAME + " ( " + UserInfo_Conlumns._ID
				+ " INTEGER PRIMARY KEY, " + PERSONID + " TEXT, " + WATCHID + " TEXT, " + EMAIL + " TEXT, " + PWD
				+ " TEXT, " + PHONENUM + " TEXT, " + USERNAME + " TEXT, " + SUMMARYTIMER + " TEXT, " + ARTICLETIMER
				+ " TEXT, " + TARGETALERTTIMER + " TEXT)";
	}

	private String personId = "";
	private String watchId = "";
	// 增加
	private String email = "";
	private String pwd = "";

	private String phoneNum = "";
	private String userName = "";

	private String summaryTimerEnabled = "";

	private String articleTimerEnabled = "";

	private String targetAlertTimerEnabled = "";

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSummaryTimerEnabled() {
		return summaryTimerEnabled;
	}

	public void setSummaryTimerEnabled(String summaryTimerEnabled) {
		this.summaryTimerEnabled = summaryTimerEnabled;
	}

	public String getArticleTimerEnabled() {
		return articleTimerEnabled;
	}

	public void setArticleTimerEnabled(String articleTimerEnabled) {
		this.articleTimerEnabled = articleTimerEnabled;
	}

	public String getTargetAlertTimerEnabled() {
		return targetAlertTimerEnabled;
	}

	public void setTargetAlertTimerEnabled(String targetAlertTimerEnabled) {
		this.targetAlertTimerEnabled = targetAlertTimerEnabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getWatchId() {
		return watchId;
	}

	public void setWatchId(String watchId) {
		this.watchId = watchId;
	}
}
