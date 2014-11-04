/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: Constants.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/19  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.common;

import java.util.List;
import java.util.Map;

import com.xikang.calorie.domain.ProfileInfo;
import com.xikang.calorie.domain.UserInfo;

/**
 * 
 * 全局常量类
 * 
 * 
 * <pre>
 * 
 * 2011/06/19, 张荣
 * 
 * 修订履历：2011/06/19 张荣   初版
 * 
 * 修订履历：2011/06/20 曲磊   增加用户信息全局变量
 * 
 * 修订履历：2011/06/23 曲磊   页面标记
 * </pre>
 * 
 * @author 张荣
 * @version 1.00
 */
public class Constants {

	public static boolean IS_LOGIN = false; // 判断登陆
	
	public static boolean IS_SERVICE_RUN = false; // 判断服务开启

	public static UserInfo CURRENT_USER = null; // 用户信息全局变量

	public static ProfileInfo CURRENT_PROFILE = null; // 个人基本信息

	public static final int PAGE_SETTING_ACCOUNT = 1001; // 账户管理页标记
	public static final int RESULT_REMAIN_SAVE = 1002;  // 提醒设置保存
	public static final int RESULT_REMAIN_COMPLETE = 1003;  // 提醒设置保存

	public static List<Map<String, String>> sportTitleList = null;

	public static int[] TIMESEGMENT = { 6, 30, 90 };

	public static Map<String, String> historyTimeResult;
}
