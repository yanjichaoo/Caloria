package com.xikang.calorie.activity;

import android.app.Application;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;

public class MapApp extends Application {
	
	static MapApp mDemoApp;

	// 百度MapAPI的管理类
	BMapManager mBMapMan = null;

	// 授权Key
	// TODO: 请输入您的Key,
	// 申请地址：http://dev.baidu.com/wiki/static/imap/key/
	String mStrKey = "F4C9812CA920CC2333A1CBBE52B9FB8FE7DDDFE7";
	boolean m_bKeyRight = true; // 授权Key正确，验证通过

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Toast.makeText(MapApp.mDemoApp.getApplicationContext(), "您的网络出错啦！", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(MapApp.mDemoApp.getApplicationContext(), "请在BMapApiDemoApp.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				MapApp.mDemoApp.m_bKeyRight = false;
			}
		}

	}

	@Override
	public void onCreate() {
		mDemoApp = this;
		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		super.onCreate();
	}

	@Override
	// 建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onTerminate();
	}
}
