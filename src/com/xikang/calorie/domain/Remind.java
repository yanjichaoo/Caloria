package com.xikang.calorie.domain;

import java.io.Serializable;

import android.provider.BaseColumns;

/**
 * 
 * 卡路里提醒信息类
 * 
 * 
 * <pre>
 * 
 * 2011/08/18, 闫继超
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 闫继超
 * @version 1.00
 */

public class Remind implements Serializable {

	private static final long serialVersionUID = 3616135930487199630L;

	public static final class Remind_Columns implements BaseColumns {
		public static final String TABLE_NAME = "Remind";
		public static final String TIMETYPE = "timeType";
		public static final String TARGETRATE = "targetRate";
		public static final String REMINDINGENABLED = "remindingEnabled";
		public static final String REMINDINGTIME = "remindingTime";
		public static final String REMINDINGREPEAT = "remindingRepeat";
		public static final String RINGTYPE = "ringType";
		public static final String TARGETCALORIE = "targetCalorie";

		public static final String T_CREATE = " create table " + TABLE_NAME + " ( " + Remind_Columns._ID
				+ " INTEGER PRIMARY KEY, " + TIMETYPE + " TEXT, " + TARGETRATE + " TEXT, " + REMINDINGENABLED
				+ " TEXT, " + REMINDINGTIME + " TEXT, " + REMINDINGREPEAT + " TEXT, " + RINGTYPE + " TEXT, "
				+ TARGETCALORIE + " TEXT) ";
	}

	private String timeType; // 运动时间类型
	private String targetRate; // 运动目标完成的预定比率
	private String remindingEnabled; // 是否进行提醒
	private String remindingTime; // 提醒时间
	private String remindingRepeat;
	private String ringType; // 提醒声音标识

	private String targetCalorie;	//目标卡路里

	public String getTargetCalorie() {
		return targetCalorie;
	}

	public void setTargetCalorie(String targetCalorie) {
		this.targetCalorie = targetCalorie;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getTargetRate() {
		return targetRate;
	}

	public void setTargetRate(String targetRate) {
		this.targetRate = targetRate;
	}

	public String getRemindingEnabled() {
		return remindingEnabled;
	}

	public void setRemindingEnabled(String remindingEnabled) {
		this.remindingEnabled = remindingEnabled;
	}

	public String getRemindingTime() {
		return remindingTime;
	}

	public void setRemindingTime(String remindingTime) {
		this.remindingTime = remindingTime;
	}

	public String getRemindingRepeat() {
		return remindingRepeat;
	}

	public void setRemindingRepeat(String remindingRepeat) {
		this.remindingRepeat = remindingRepeat;
	}

	public String getRingType() {
		return ringType;
	}

	public void setRingType(String ringType) {
		this.ringType = ringType;
	}

}
