/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: CityService.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.xikang.calorie.domain.City;
import com.xikang.calorie.domain.City.City_Conlumns;

/**
 * 
 * 城市数据库操作类
 * 
 * 
 * <pre>
 * 
 * 2011/06/16, 曲磊
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
public class CityService extends DB_Provider {

	public CityService(DB_Interface mInterface) {
		super(mInterface);
	}

	/**
	 * 插入数据
	 * 
	 * @param city
	 */
	public void insertCity(City city) {
		ContentValues values = new ContentValues();
		values.put(City_Conlumns.PARENTID, city.getParentId());
		values.put(City_Conlumns.CODE, city.getCode());
		values.put(City_Conlumns.NAME, city.getName());
		dbWriteOpen().insert(City_Conlumns.TABLE_NAME, null, values);
		values.clear();
	}

	/**
	 * 更新正表数据
	 * 
	 * @param cityList
	 * @return
	 */
	public boolean updateTable(List<City> cityList) {

		if (cityList == null)
			return false;

		boolean isSuccess = false;
		dbWriteOpen().beginTransaction();
		try {
			deleteTable();
			for (City city : cityList) {
				insertCity(city);
			}
			dbWriteOpen().setTransactionSuccessful();
			isSuccess = true;
		} catch (Exception e) {
			dbWriteOpen().endTransaction();
		}
		dbWriteOpen().endTransaction();
		return isSuccess;
	}

	/**
	 * 查询城市列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<City> getCityList(String parentId) {
		List<City> dataList = null;
		Cursor cursor = dbReadOpen().query(City_Conlumns.TABLE_NAME, null, City_Conlumns.PARENTID + "=?",
				new String[] { parentId }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				dataList = new ArrayList<City>();
				do {
					City city = new City();
					city.setCode(cursor.getString(cursor.getColumnIndex(City_Conlumns.CODE)));
					city.setName(cursor.getString(cursor.getColumnIndex(City_Conlumns.NAME)));
					city.setParentId(cursor.getString(cursor.getColumnIndex(City_Conlumns.PARENTID)));
					dataList.add(city);
				} while (cursor.moveToNext());
			}
		}
		closeCursor(cursor);
		return dataList;
	}

	public City getCity(String code) {
		City city = null;
		Cursor cursor = dbReadOpen().query(City_Conlumns.TABLE_NAME, null, City_Conlumns.CODE + "=?",
				new String[] { code }, null, null, null);
		if (cursor != null && cursor.getCount() >= 0) {
			if (cursor.moveToFirst()) {
				do {
					city = new City();
					city.setCode(cursor.getString(cursor.getColumnIndex(City_Conlumns.CODE)));
					city.setName(cursor.getString(cursor.getColumnIndex(City_Conlumns.NAME)));
					city.setParentId(cursor.getString(cursor.getColumnIndex(City_Conlumns.PARENTID)));
				} while (cursor.moveToNext());
			}
		}
		closeCursor(cursor);
		return city;
	}

	public void deleteTable() {
		dbWriteOpen().delete(City_Conlumns.TABLE_NAME, null, null);
	}

}
