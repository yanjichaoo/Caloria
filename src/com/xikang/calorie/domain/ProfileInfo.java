/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: ProfileInfo.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.domain;

import com.xikang.calorie.xml.XmlOpe;

/**
 * 
 * 个人基本信息类
 * 
 * 
 * <pre>
 * 
 * 2011/06/16, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class ProfileInfo {

	private String birthday;
	private String sex;
	private String height;
	private String weight;
	private String targetWeight;
	private String cityCode;
	private String vitality;

	private static ProfileInfo INSTANCE;
	private static Object INSTANCE_LOCK = new Object();

	public static ProfileInfo getInstance(XmlOpe xmlOpe, String personId) {
		if (INSTANCE == null)
			synchronized (INSTANCE_LOCK) {
				if (INSTANCE == null) {
					INSTANCE = xmlOpe.getProfileInfo(personId);
				}
			}

		return INSTANCE;
	}

	public static void clearCache() {
		INSTANCE = null;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(String targetWeight) {
		this.targetWeight = targetWeight;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getVitality() {
		return vitality;
	}

	public void setVitality(String vitality) {
		this.vitality = vitality;
	}

}
