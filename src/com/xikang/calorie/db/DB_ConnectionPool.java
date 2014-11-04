/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: DB_ConnectionPool.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB_ConnectionPool extends DB_Interface {

	private static DB_Helper db_Helper;

	private static DB_ConnectionPool INSTANCE;
	private static Object INSTANCE_LOCK = new Object();

	private DB_ConnectionPool() {
	}

	public static DB_ConnectionPool getInstance(Context context) {
		if (INSTANCE == null)
			synchronized (INSTANCE_LOCK) {
				if (INSTANCE == null) {
					INSTANCE = new DB_ConnectionPool();
				}
				init(context);
			}

		return INSTANCE;
	}

	static void init(Context context) {
		db_Helper = new DB_Helper(context);
	}
	
	@Override
	void dbClose() {
		db_Helper.close();
	}

	@Override
	void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
            dbClose();
        }
	}

	@Override
	SQLiteDatabase dbReadOpen() {
		return db_Helper.getReadableDatabase();
	}

	@Override
	SQLiteDatabase dbWriteOpen() {
		return db_Helper.getWritableDatabase();
	}
}