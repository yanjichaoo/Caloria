/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SportTip.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/08/11  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * 小贴士正文页
 * 
 * 
 * <pre>
 * 
 * 2011/08/11, 张荣
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 张荣
 * @version 1.00
 */
public class SportTip extends Activity  {

	private TextView mTvTBContent;
	private Button mBtnTBLeft;
	private TextView mTipTitle;
	private TextView mTipSummary;
	
	private String tipTitle;
	private String tipSummary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sport_tip);	
		Bundle bundle = getIntent().getExtras();
		tipTitle = bundle.getString("tipTitle");
		tipSummary = bundle.getString("tipSummary");
		init();
	}

	private void init() {

		// 页标题设置
		mTvTBContent = (TextView) findViewById(R.id.tvTBContent);
		mTvTBContent.setText(R.string.tip_title);

		// 标题按钮设置
		mBtnTBLeft = (Button) findViewById(R.id.btnTBLeft);
		mBtnTBLeft.setText(R.string.common_back);
		mBtnTBLeft.setVisibility(View.VISIBLE);
		mBtnTBLeft.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SportTip.this.finish();
			}
		});
		
		// 标题设置
		mTipTitle = (TextView) findViewById(R.id.tip_title);
		mTipTitle.setText(tipTitle==null?"":tipTitle);

		// 内容设置
		mTipSummary = (TextView) findViewById(R.id.tip_summary);
		mTipSummary.setText(tipSummary==null?"":tipSummary);
		
	}
}
