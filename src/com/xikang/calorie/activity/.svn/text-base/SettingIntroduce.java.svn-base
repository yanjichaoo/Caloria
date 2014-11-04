/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SettingIntroduce.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xikang.calorie.common.ActivityJump;


/**
 * 
 * 设置-应用介绍界面
 * 
 * <pre>
 * 
 * 2011/07/20, 曲磊
 *
 * 修订履历：2011/08/10 曲磊  优化程序
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class SettingIntroduce extends BaseActivity {

	
	private FrameLayout lytSIAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_introduce);
		init();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		
		showTitle(getString(R.string.setting_layout_introduce));
		
		showLeftButton(getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ActivityGroup ag = (ActivityGroup) SettingIntroduce.this.getParent();
				ActivityJump.JumpSetting(ag, (LinearLayout) ag.getWindow().findViewById(R.id.layoutAG));
			}
		});
		
		
		lytSIAbout = (FrameLayout)findViewById(R.id.lytSIAbout);
		lytSIAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, ProductIntroduce.class));
			}
		});

	}

}
