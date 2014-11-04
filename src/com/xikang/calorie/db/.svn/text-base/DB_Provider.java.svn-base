/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: DB_Provider.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB_Provider extends DB_Interface {

	private DB_Interface mInterface;

	public DB_Provider(DB_Interface mInterface) {
		this.mInterface = mInterface;
	}
	
	@Override
	void dbClose() {
		mInterface.dbClose();
	}

	@Override
	void closeCursor(Cursor cursor) {
		mInterface.closeCursor(cursor);
	}

	@Override
	SQLiteDatabase dbReadOpen() {
		return mInterface.dbReadOpen();
	}

	@Override
	SQLiteDatabase dbWriteOpen() {
		return mInterface.dbWriteOpen();
	}
}
