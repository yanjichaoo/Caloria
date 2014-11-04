/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: RankInfo.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/15  REV.  备注
 *         2011/06/15  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.domain;

import android.provider.BaseColumns;

public class RankInfo {
	public static final class Rank_Conlumns implements BaseColumns {
		public static final String TABLE_NAME = "Rank";

		public static final String ALLRANK = "allRank";

		public static final String CITYRANK = "cityRank";

		public static final String AGERANK = "ageRank";

		public static final String GETTIME = "getTime";

		public static final String T_CREATE = "create table " + TABLE_NAME + " ( " + Rank_Conlumns._ID
				+ " INTEGER PRIMARY KEY, " + ALLRANK + " INTEGER, " + CITYRANK + " INTEGER, " + AGERANK + " INTEGER, "
				+ GETTIME + " INTEGER)";
	}

	private int allRank;
	private int cityRank;
	private int ageRank;

	public int getAllRank() {
		return allRank;
	}

	public void setAllRank(int allRank) {
		this.allRank = allRank;
	}

	public int getCityRank() {
		return cityRank;
	}

	public void setCityRank(int cityRank) {
		this.cityRank = cityRank;
	}

	public int getAgeRank() {
		return ageRank;
	}

	public void setAgeRank(int ageRank) {
		this.ageRank = ageRank;
	}

}