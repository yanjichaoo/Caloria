/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: RankVO.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/15  REV.  备注
 *         2011/06/15  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 排行榜数据类
 * 
 * 
 * <pre>
 * 
 * 2011/06/15, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class RankVO {

	private String beatPresent;

	private String currentRank;

	private int rankType;

	private int changeRank;
	
	private String cityCode;
	
	private String brithday;

	private List<Map<String, String>> rank = new ArrayList<Map<String, String>>();

	public String getBeatPresent() {
		return beatPresent;
	}

	public void setBeatPresent(String beatPresent) {
		this.beatPresent = beatPresent;
	}

	public String getCurrentRank() {
		return currentRank;
	}

	public void setCurrentRank(String currentRank) {
		this.currentRank = currentRank;
	}

	public int getRankType() {
		return rankType;
	}

	public void setRankType(int rankType) {
		this.rankType = rankType;
	}

	public int getChangeRank() {
		return changeRank;
	}

	public void setChangeRank(int changeRank) {
		this.changeRank = changeRank;
	}

	public List<Map<String, String>> getRank() {
		return rank;
	}

	public void setRank(List<Map<String, String>> rank) {
		this.rank = rank;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

}
