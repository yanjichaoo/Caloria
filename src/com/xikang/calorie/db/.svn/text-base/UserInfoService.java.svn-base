/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: UserInfoService.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.xikang.calorie.domain.UserInfo;
import com.xikang.calorie.domain.RankInfo.Rank_Conlumns;
import com.xikang.calorie.domain.Remind.Remind_Columns;
import com.xikang.calorie.domain.UserInfo.UserInfo_Conlumns;

/**
 * 
 * 用户信息数据库操作类
 * 
 * 
 * <pre>
 * 
 * 2011/06/20, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * 
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class UserInfoService extends DB_Provider {

	public UserInfoService(DB_Interface mInterface) {
		super(mInterface);
	}

	/**
	 * 插入数据
	 * @param userInfo
	 */
	public void saveUserInfo(UserInfo userInfo) {
		Cursor cursor = dbReadOpen().query(UserInfo_Conlumns.TABLE_NAME, null,
				UserInfo_Conlumns.PERSONID + "=? and " + UserInfo_Conlumns.EMAIL + "=?",
				new String[] { userInfo.getPersonId(), userInfo.getEmail() }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				ContentValues values = new ContentValues();
				values.put(UserInfo_Conlumns.WATCHID, userInfo.getWatchId());
				values.put(UserInfo_Conlumns.PWD, userInfo.getPwd());
				values.put(UserInfo_Conlumns.PHONENUM, userInfo.getPhoneNum());
				values.put(UserInfo_Conlumns.USERNAME, userInfo.getUserName());
				values.put(UserInfo_Conlumns.SUMMARYTIMER, userInfo.getSummaryTimerEnabled());
				values.put(UserInfo_Conlumns.ARTICLETIMER, userInfo.getArticleTimerEnabled());
				values.put(UserInfo_Conlumns.TARGETALERTTIMER, userInfo.getTargetAlertTimerEnabled());
				dbWriteOpen().update(UserInfo_Conlumns.TABLE_NAME, values,
						UserInfo_Conlumns.PERSONID + "=? and " + UserInfo_Conlumns.EMAIL + "=?",
						new String[] { userInfo.getPersonId(), userInfo.getEmail() });
				values.clear();
			}
		} else {
			ContentValues values = new ContentValues();
			values.put(UserInfo_Conlumns.PERSONID, userInfo.getPersonId());
			values.put(UserInfo_Conlumns.WATCHID, userInfo.getWatchId());
			values.put(UserInfo_Conlumns.EMAIL, userInfo.getEmail());
			values.put(UserInfo_Conlumns.PWD, userInfo.getPwd());
			values.put(UserInfo_Conlumns.PHONENUM, userInfo.getPhoneNum());
			values.put(UserInfo_Conlumns.USERNAME, userInfo.getUserName());
			values.put(UserInfo_Conlumns.SUMMARYTIMER, userInfo.getSummaryTimerEnabled());
			values.put(UserInfo_Conlumns.ARTICLETIMER, userInfo.getArticleTimerEnabled());
			values.put(UserInfo_Conlumns.TARGETALERTTIMER, userInfo.getTargetAlertTimerEnabled());
			dbWriteOpen().insert(UserInfo_Conlumns.TABLE_NAME, null, values);
			values.clear();
		}
		closeCursor(cursor);
	}

	/**
	 * 查询最新的一条用户数据
	 * 
	 * @return
	 */
	public UserInfo getCurrentUserInfo() {
		UserInfo userInfo = null;
		Cursor cursor = dbReadOpen().query(UserInfo_Conlumns.TABLE_NAME, null, null, null, null, null,
				UserInfo_Conlumns._ID + " desc");
		if (cursor != null && cursor.getCount() >= 0) {
			if (cursor.moveToPosition(0)) {
				userInfo = new UserInfo();
				userInfo.setEmail(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.EMAIL)));
				userInfo.setPersonId(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.PERSONID)));
				userInfo.setPwd(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.PWD)));
				userInfo.setWatchId(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.WATCHID)));
				userInfo.setPhoneNum(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.PHONENUM)));
				userInfo.setUserName(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.USERNAME)));
				userInfo.setSummaryTimerEnabled(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.SUMMARYTIMER)));
				userInfo.setArticleTimerEnabled(cursor.getString(cursor.getColumnIndex(UserInfo_Conlumns.ARTICLETIMER)));
				userInfo.setTargetAlertTimerEnabled(cursor.getString(cursor
						.getColumnIndex(UserInfo_Conlumns.TARGETALERTTIMER)));
			}
		}
		closeCursor(cursor);
		return userInfo;
	}
	
	public void deleteTable(){
		dbWriteOpen().delete(UserInfo_Conlumns.TABLE_NAME, null, null);
		dbWriteOpen().delete(Remind_Columns.TABLE_NAME, null, null);
		dbWriteOpen().delete(Rank_Conlumns.TABLE_NAME, null, null);
	}
}
