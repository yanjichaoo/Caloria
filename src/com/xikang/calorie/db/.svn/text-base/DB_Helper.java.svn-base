/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: DB_Helper.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.db;

import java.io.InputStream;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xikang.calorie.common.Util;
import com.xikang.calorie.domain.City;
import com.xikang.calorie.domain.City.City_Conlumns;
import com.xikang.calorie.domain.RankInfo.Rank_Conlumns;
import com.xikang.calorie.domain.Remind.Remind_Columns;
import com.xikang.calorie.domain.UserInfo.UserInfo_Conlumns;
import com.xikang.calorie.xml.AreaXmlParser;

public class DB_Helper extends SQLiteOpenHelper {
	private Context mContext;

	DB_Helper(Context context) {
		super(context, DB_Constants.DB_NAME, null, DB_Constants.DB_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(City_Conlumns.T_CREATE);
		db.execSQL(UserInfo_Conlumns.T_CREATE);
		db.execSQL(Rank_Conlumns.T_CREATE);
		db.execSQL(Remind_Columns.T_CREATE);
		addAreaData(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + City_Conlumns.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + UserInfo_Conlumns.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Rank_Conlumns.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Remind_Columns.TABLE_NAME);
		onCreate(db);
	}

	private void addAreaData(SQLiteDatabase db) {
		AssetManager assetManager = mContext.getAssets();
		try {
			InputStream inputStream = assetManager.open("area.xml");
			String writeBack = Util.readTextFile(inputStream);
			
			List<City> list = AreaXmlParser.parseXml(writeBack);
			for (City city : list) {
				insertCity(city,db);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertCity(City city, SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(City_Conlumns.PARENTID, city.getParentId());
		values.put(City_Conlumns.CODE, city.getCode());
		values.put(City_Conlumns.NAME, city.getName());
		db.insert(City_Conlumns.TABLE_NAME, null, values);
		values.clear();
	}
}
