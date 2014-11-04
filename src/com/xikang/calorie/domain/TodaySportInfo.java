/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: TodaySportInfo.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/19  REV.  备注
 *         2011/06/19  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */

package com.xikang.calorie.domain;

/**
 * 
 * 今日运动信息类。
 * 
 * 
 * <pre>
 * 
 * 2011/06/19, 张荣
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 张荣
 * @version 1.00
 */
public class TodaySportInfo {

	private String hasData;
	private String todaySportTarget;
	private String userName;
	private String stepCount;
	private String distance;
	private String calorie;
	private String averageHeartRate;

	private String tipTitle;

	public String getTipTitle() {
		return tipTitle;
	}

	public void setTipTitle(String tipTitle) {
		this.tipTitle = tipTitle;
	}

	public String getTipSummary() {
		return tipSummary;
	}

	public void setTipSummary(String tipSummary) {
		this.tipSummary = tipSummary;
	}

	public String getTipContent() {
		return tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}

	private String tipSummary;
	private String tipContent;

	private String elapsedTime;
	private String averageSpeed;

	public String getHasData() {
		return hasData;
	}

	public void setHasData(String hasData) {
		this.hasData = hasData;
	}

	public String getTodaySportTarget() {
		return todaySportTarget;
	}

	public void setTodaySportTarget(String todaySportTarget) {
		this.todaySportTarget = todaySportTarget;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStepCount() {
		return stepCount;
	}

	public void setStepCount(String stepCount) {
		this.stepCount = stepCount;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCalorie() {
		return calorie;
	}

	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}

	public String getAverageHeartRate() {
		return averageHeartRate;
	}

	public void setAverageHeartRate(String averageHeartRate) {
		this.averageHeartRate = averageHeartRate;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(String averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
}
