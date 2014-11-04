/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: RankService.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/28  REV.  备注
 *
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

import com.xikang.calorie.domain.RankInfo.Rank_Conlumns;

/**
 * 
 * 排行榜信息数据库操作类
 * 
 * 
 * <pre>
 * 
 * 2011/06/28, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * 
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class RankService extends DB_Provider {

	public RankService(DB_Interface mInterface) {
		super(mInterface);
	}
	
	public void insertRank(Map<String ,Integer> rampMap) {
		ContentValues values = new ContentValues();
		values.put(Rank_Conlumns.ALLRANK, rampMap.get(Rank_Conlumns.ALLRANK));
		values.put(Rank_Conlumns.CITYRANK, rampMap.get(Rank_Conlumns.CITYRANK));
		values.put(Rank_Conlumns.AGERANK, rampMap.get(Rank_Conlumns.AGERANK));
		values.put(Rank_Conlumns.GETTIME, (new Date()).getTime());
		dbWriteOpen().insert(Rank_Conlumns.TABLE_NAME, null, values);
		values.clear();
	}
	public void deleteTable(){
		dbWriteOpen().delete(Rank_Conlumns.TABLE_NAME, null, null);
	}
	
	public Map<String ,Integer> getLastRak(){
		Map<String, Integer> lastRank=null;
		Cursor cursor = dbReadOpen().query(Rank_Conlumns.TABLE_NAME,new String[]{ " * "}, null, null, null, null, Rank_Conlumns.GETTIME + " desc");
		if (cursor != null && cursor.getCount() >= 0) {
			if (cursor.moveToFirst()) {
				do {
					lastRank=new HashMap<String, Integer>();
					
					lastRank.put(Rank_Conlumns.ALLRANK, cursor.getInt(cursor.getColumnIndex(Rank_Conlumns.ALLRANK)));
					lastRank.put(Rank_Conlumns.CITYRANK, cursor.getInt(cursor.getColumnIndex(Rank_Conlumns.CITYRANK)));
					lastRank.put(Rank_Conlumns.AGERANK, cursor.getInt(cursor.getColumnIndex(Rank_Conlumns.AGERANK)));
				} while (cursor.moveToNext());
			}
		}
		closeCursor(cursor);
		return lastRank;
	}
}
