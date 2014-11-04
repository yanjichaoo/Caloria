/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: ActivityJump.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/23  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.common;

import android.app.ActivityGroup;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.xikang.calorie.activity.Setting;
import com.xikang.calorie.activity.SettingIntroduce;

/**
 * 
 * 页面跳转
 * 
 * 
 * <pre>
 * 
 * 2011/06/23, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class ActivityJump {

	/**
	 * 设置页
	 * @param ag
	 * @param linearLayout
	 */
	public static void JumpSetting(ActivityGroup ag, LinearLayout linearLayout) {
		linearLayout.removeAllViews();
		Intent mIntent = new Intent(ag.getApplicationContext(), Setting.class);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window mWindow = ag.getLocalActivityManager().startActivity("Setting", mIntent);
		View mView = mWindow.getDecorView();
		LayoutParams paramView = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		linearLayout.addView(mView, paramView);
	}
	
	public static void JumpSettingIntroduce(ActivityGroup ag, LinearLayout linearLayout) {
		linearLayout.removeAllViews();
		Intent mIntent = new Intent(ag.getApplicationContext(), SettingIntroduce.class);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window mWindow = ag.getLocalActivityManager().startActivity("SettingIntroduce", mIntent);
		View mView = mWindow.getDecorView();
		LayoutParams paramView = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		linearLayout.addView(mView, paramView);
	}
	
}