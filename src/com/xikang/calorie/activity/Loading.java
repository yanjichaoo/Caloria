/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: Loading.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.Locale;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.UserInfoService;
import com.xikang.calorie.notification.CalorieService;
import com.xikang.calorie.xml.XmlConstants;

/**
 * 
 * 首页登录判断界面
 * 
 * 
 * <pre>
 * 
 * 2011/06/21, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 修订履历：2011/07/13 曲磊  页面样式和等待动画
 * 修订履历：2011/08/10 曲磊  优化程序
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class Loading extends BaseActivity {

	public static final int PAGE_PRODUCT_INTRODUCE = 8001; // 产品介绍页标记

	private LoadingThread thread = null;
	private UserInfoService mUserInfoService;

	private ImageView ivLoadRun;
	private AnimationDrawable animDraw = null;
	private Handler runHandler = new Handler();

	private boolean isExit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_loading);

		mUserInfoService = new UserInfoService(DB_ConnectionPool.getInstance(mContext));

		ivLoadRun = (ImageView) findViewById(R.id.ivLoadRun);
		animDraw = (AnimationDrawable) ivLoadRun.getDrawable();
		System.out.println(Locale.getDefault().getLanguage()+"##"+Locale.getDefault().getLanguage());
		thread = new LoadingThread();
		thread.start();
		
		if(!Constants.IS_SERVICE_RUN){
			Intent it = new Intent(this, CalorieService.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(it);
		}

	}

	@Override
	protected void onPause() {
		isExit = true;
		runHandler.removeCallbacks(runLoadingImg);
		animDraw.stop();
		super.onPause();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case PAGE_PRODUCT_INTRODUCE:
			startActivity(new Intent(mContext, MainFrame.class));
			this.finish();
			break;
		}
	}

	/**
	 * 图片动画线程
	 */
	private Runnable runLoadingImg = new Runnable() {
		public void run() {
			if (animDraw.isRunning()) {
				animDraw.stop();
			} else {
				animDraw.stop();
				animDraw.start();
			}
		}
	};

	/**
	 * 登录消息处理
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (isExit)
				return;

			switch (msg.what) {
			case LOADING_SCUESS:
				Bundle bundle = msg.getData();
				int status = bundle.getInt("status");
				if (status == 1) {
					Loading.this.startActivity(new Intent(mContext, MainFrame.class));
					Loading.this.finish();
				} else {
					startActivityForResult(new Intent(mContext, ProductIntroduce.class), PAGE_PRODUCT_INTRODUCE);
				}
				break;
			}
		}
	};

	/**
	 * 登录线程
	 * 
	 * @author qulei
	 * 
	 */
	class LoadingThread extends Thread {
		@Override
		public void run() {

			int status = 0;
			runHandler.postDelayed(runLoadingImg, 500);

			Constants.CURRENT_USER = mUserInfoService.getCurrentUserInfo();
			if (Constants.CURRENT_USER != null) {
				Map<String, String> result = mXmlope.loginUser(Constants.CURRENT_USER);
				if (result != null) {
					status = Integer.valueOf(result.get(XmlConstants.STATUS));
					if (status == 1) {
						Constants.IS_LOGIN = true;
					}
				}
			} else {
				try {
					sleep(3000);
				} catch (InterruptedException e) {

				}
			}

			Message msg = handler.obtainMessage();
			Bundle bundle = new Bundle();
			msg.setData(bundle);
			bundle.putInt("status", status);
			msg.what = LOADING_SCUESS;
			handler.sendMessage(msg);
		}
	}

}