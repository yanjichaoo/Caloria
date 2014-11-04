/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: DB_Interface.java
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

public abstract class DB_Interface {
	
	abstract SQLiteDatabase dbReadOpen();
	abstract SQLiteDatabase dbWriteOpen();
	abstract void dbClose();
	abstract void closeCursor(Cursor cursor);

}