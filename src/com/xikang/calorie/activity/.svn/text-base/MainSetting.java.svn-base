/* ==================================================
 * 产品名: 卡路里管理
 * 文件名: MainSetting.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.xikang.calorie.common.ActivityJump;

public class MainSetting extends ActivityGroup {
	private LinearLayout mLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_group);
		mLinearLayout = (LinearLayout) findViewById(R.id.layoutAG);
		ActivityJump.JumpSetting(this, mLinearLayout);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
