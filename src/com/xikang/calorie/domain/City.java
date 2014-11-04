/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: City.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.domain;

import java.util.List;

import android.provider.BaseColumns;
/**
 * 
 * 居住城市类
 * 
 * 
 * <pre>
 * 
 * 2011/06/16, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class City {

	public static final class City_Conlumns implements BaseColumns {

		public static final String TABLE_NAME = "City";
		public static final String PARENTID = "parentId";
		public static final String CODE = "code";
		public static final String NAME = "name";

		public static final String T_CREATE = "create table " + TABLE_NAME + " ( " + City_Conlumns._ID
				+ " INTEGER PRIMARY KEY, " + PARENTID + " TEXT, " + CODE + " TEXT, " + NAME + " TEXT)";
	}

	private String parentId;
	private String code;
	private String name;
	
	private List<City> list;

	public List<City> getList() {
		return list;
	}

	public void setList(List<City> list) {
		this.list = list;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
