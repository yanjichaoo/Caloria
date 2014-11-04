package com.xikang.calorie.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.xikang.calorie.domain.Remind;
import com.xikang.calorie.domain.Remind.Remind_Columns;

/**
 * 
 * 用户信息数据库操作类
 * 
 * 
 * <pre>
 * 
 * 2011/08/17, 闫继超
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * 
 * 
 * </pre>
 * 
 * @author 闫继超
 * @version 1.00
 */

public class RemindService extends DB_Provider {

	public RemindService(DB_Interface mInterface) {
		super(mInterface);
	}

	/**
	 * 插入数据
	 * 
	 * @param remind
	 */
	public void saveRemind(Remind remind) {
		Cursor cursor = dbReadOpen().query(Remind_Columns.TABLE_NAME, null, Remind_Columns.TIMETYPE + " =? ",
				new String[] { remind.getTimeType() }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				ContentValues values = new ContentValues();
				values.put(Remind_Columns.TIMETYPE, remind.getTimeType());
				values.put(Remind_Columns.TARGETRATE, remind.getTargetRate());
				values.put(Remind_Columns.REMINDINGENABLED, remind.getRemindingEnabled());
				values.put(Remind_Columns.REMINDINGTIME, remind.getRemindingTime());
				values.put(Remind_Columns.REMINDINGREPEAT, remind.getRemindingRepeat());
				values.put(Remind_Columns.RINGTYPE, remind.getRingType());
				values.put(Remind_Columns.TARGETCALORIE, remind.getTargetCalorie());
				dbWriteOpen().update(Remind_Columns.TABLE_NAME, values, Remind_Columns.TIMETYPE + " =? ",
						new String[] { remind.getTimeType() });
				values.clear();
			}
		} else {
			ContentValues values = new ContentValues();
			values.put(Remind_Columns.TIMETYPE, remind.getTimeType());
			values.put(Remind_Columns.TARGETRATE, remind.getTargetRate());
			values.put(Remind_Columns.REMINDINGENABLED, remind.getRemindingEnabled());
			values.put(Remind_Columns.REMINDINGTIME, remind.getRemindingTime());
			values.put(Remind_Columns.REMINDINGREPEAT, remind.getRemindingRepeat());
			values.put(Remind_Columns.RINGTYPE, remind.getRingType());
			values.put(Remind_Columns.TARGETCALORIE, remind.getTargetCalorie());
			dbWriteOpen().insert(Remind_Columns.TABLE_NAME, null, values);
			values.clear();
		}
		closeCursor(cursor);
	}
	
	/**
	 * 根据当前时间类型获取实体
	 * @param timeType
	 * @return
	 */
	public Remind getCurrentRemind(String timeType) {
		Remind remind = null;
		Cursor cursor = dbReadOpen().query(Remind_Columns.TABLE_NAME, null, Remind_Columns.TIMETYPE + " =? ",
				new String[] { timeType }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				remind = new Remind();
				remind.setTimeType(cursor.getString(cursor.getColumnIndex(Remind_Columns.TIMETYPE)));
				remind.setTargetRate(cursor.getString(cursor.getColumnIndex(Remind_Columns.TARGETRATE)));
				remind.setRemindingEnabled(cursor.getString(cursor.getColumnIndex(Remind_Columns.REMINDINGENABLED)));
				remind.setRemindingTime(cursor.getString(cursor.getColumnIndex(Remind_Columns.REMINDINGTIME)));
				remind.setRemindingRepeat(cursor.getString(cursor.getColumnIndex(Remind_Columns.REMINDINGREPEAT)));
				remind.setRingType(cursor.getString(cursor.getColumnIndex(Remind_Columns.RINGTYPE)));
				remind.setTargetCalorie(cursor.getString(cursor.getColumnIndex(Remind_Columns.TARGETCALORIE)));
			}
		}
		closeCursor(cursor);
		return remind;
	}
	/** 
	 * 修改是否提醒
	 * @param timeType
	 * @param remind
	 */
	public void updataRemindEnabled(String timeType, String remind) {
		Cursor cursor = dbReadOpen().query(Remind_Columns.TABLE_NAME, null, Remind_Columns.TIMETYPE + " =? ",
				new String[] { timeType }, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				ContentValues values = new ContentValues();
				values.put(Remind_Columns.REMINDINGENABLED, remind);
				dbWriteOpen().update(Remind_Columns.TABLE_NAME, values, Remind_Columns.TIMETYPE + " =? ",
						new String[] { timeType });
				values.clear();
			}
		} else {
			ContentValues values = new ContentValues();
			values.put(Remind_Columns.REMINDINGENABLED, remind);
			dbWriteOpen().insert(Remind_Columns.TABLE_NAME, null, values);
			values.clear();
		}
		closeCursor(cursor);
	}
	/**
	 * 初始化运动比率
	 * @param timeType
	 * @param remind
	 */
	public void updateTargetRate(String timeType, String targetRate) {
		Cursor cursor = dbReadOpen().query(Remind_Columns.TABLE_NAME, null, Remind_Columns.TIMETYPE + " =? ",
				new String[] { timeType }, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				ContentValues values = new ContentValues();
				values.put(Remind_Columns.TARGETRATE, targetRate);
				dbWriteOpen().update(Remind_Columns.TABLE_NAME, values, Remind_Columns.TIMETYPE + " =? ",
						new String[] { timeType });
				values.clear();
			}
		} else {
			ContentValues values = new ContentValues();
			values.put(Remind_Columns.TARGETRATE, targetRate);
			values.put(Remind_Columns.TIMETYPE, timeType);
			dbWriteOpen().insert(Remind_Columns.TABLE_NAME, null, values);
			values.clear();
		}
		closeCursor(cursor);
	}
	/**
	 * 修改目标卡路里
	 * @param timeType
	 * @param targetRate
	 */
	public void updateTargetCalorie(String timeType, String targetCalorie) {
		Cursor cursor = dbReadOpen().query(Remind_Columns.TABLE_NAME, null, Remind_Columns.TIMETYPE + " =? ",
				new String[] { timeType }, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				ContentValues values = new ContentValues();
				values.put(Remind_Columns.TARGETCALORIE, targetCalorie);
				dbWriteOpen().update(Remind_Columns.TABLE_NAME, values, Remind_Columns.TIMETYPE + " =? ",
						new String[] { timeType });
				values.clear();
			}
		} else {
			ContentValues values = new ContentValues();
			values.put(Remind_Columns.TARGETCALORIE, targetCalorie);
			values.put(Remind_Columns.TIMETYPE, timeType);
			dbWriteOpen().insert(Remind_Columns.TABLE_NAME, null, values);
			values.clear();
		}
		closeCursor(cursor);
	}

	public void deleteTable() {
		dbWriteOpen().delete(Remind_Columns.TABLE_NAME, null, null);
	}

}
