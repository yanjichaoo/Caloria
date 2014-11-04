/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SettingAccount.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.UserInfoService;
import com.xikang.calorie.domain.ProfileInfo;
import com.xikang.calorie.domain.UserInfo;
import com.xikang.calorie.xml.XmlConstants;
import com.xikang.calorie.xml.XmlConstants.UserLogin;

/**
 * 
 * 账户管理设置界面
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：2011/06/20 曲磊  登录功能添加
 * 修订履历：2011/06/23 曲磊  添加登录加载模态窗体，并完善页面功能
 * 修订履历：2011/08/10 曲磊  优化程序
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class SettingAccount extends BaseActivity implements OnClickListener {

	// 登录控件
	private Button btnSALogin;
	private Button btnSARegister;

	// 文本控件
	private EditText etSAEmail;
	private EditText etSAPwd;
	private EditText etSAWatch;

	private UserInfoService mUserInfoService;

	private static final int HANDLER_MESSAGE_NOTICE = 8001;
	private static final int HANDLER_MESSAGE_JUMP = 8002;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_account);
		mUserInfoService = new UserInfoService(DB_ConnectionPool.getInstance(mContext));
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 500);
	}

	/**
	 * 初始化控件
	 */
	private void init() {

		showTitle(getString(R.string.setting_layout_account));

		showLeftButton(getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingAccount.this.finish();
			}
		});

		btnSALogin = (Button) findViewById(R.id.btnSALogin);
		btnSALogin.setOnClickListener(this);
		btnSARegister = (Button) findViewById(R.id.btnSARegister);
		btnSARegister.setOnClickListener(this);

		etSAEmail = (EditText) findViewById(R.id.etSAEmail);
		etSAPwd = (EditText) findViewById(R.id.etSAPwd);
		etSAWatch = (EditText) findViewById(R.id.etSAWatch);
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.btnSALogin: // 登录
			login();
			break;
		case R.id.btnSARegister: // 注册
			Bundle bundle = new Bundle();
			bundle.putString("Email", etSAEmail.getText().toString());
			bundle.putString("Watch", etSAWatch.getText().toString());

			Intent intent = new Intent(mContext, SettingRegister.class);
			intent.putExtras(bundle);
			startActivityForResult(intent, Constants.PAGE_SETTING_ACCOUNT);
			break;

		}
	}

	/**
	 * 页面返回跳转
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case Constants.PAGE_SETTING_ACCOUNT:// 跳转到登录成功页
			startActivity(new Intent(mContext, SettingLogined.class));
			this.finish();
			break;
		}
	}

	private void login() {
		showProgress(getString(R.string.setting_progress_message_login));

		new Thread() {
			public void run() {
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(etSAEmail.getText().toString());
				userInfo.setPwd(etSAPwd.getText().toString());
				userInfo.setWatchId(etSAWatch.getText().toString());
				Map<String, String> result = mXmlope.loginUser(userInfo);
				if (result != null) {
					int status = Integer.valueOf(result.get(XmlConstants.STATUS));
					if (status == 1) { // 登录成功
						String personId = result.get(UserLogin.PERSON_ID);
						UserInfo mUserInfo = mXmlope.getUserInfo(personId);
						if (mUserInfo != null) {
							Constants.CURRENT_USER = new UserInfo();
							Constants.CURRENT_USER.setPersonId(personId);// 注意传递的参数
							Constants.CURRENT_USER.setEmail(mUserInfo.getEmail());
							Constants.CURRENT_USER.setPwd(userInfo.getPwd());// 注意传递的参数
							Constants.CURRENT_USER.setWatchId(mUserInfo.getWatchId());
							Constants.CURRENT_USER.setPhoneNum(mUserInfo.getPhoneNum());
							Constants.CURRENT_USER.setUserName(mUserInfo.getUserName());
							Constants.CURRENT_USER.setSummaryTimerEnabled(mUserInfo.getSummaryTimerEnabled());
							Constants.CURRENT_USER.setArticleTimerEnabled(mUserInfo.getArticleTimerEnabled());
							Constants.CURRENT_USER.setTargetAlertTimerEnabled(mUserInfo.getTargetAlertTimerEnabled());
							mUserInfoService.saveUserInfo(Constants.CURRENT_USER);
							Constants.IS_LOGIN = true;
							Constants.CURRENT_PROFILE = ProfileInfo.getInstance(mXmlope,
									Constants.CURRENT_USER.getPersonId());

							Message msg = handlerMessage.obtainMessage();
							msg.what = HANDLER_MESSAGE_JUMP;
							handlerMessage.sendMessage(msg);
						} else { // 获取账户信息不完整
							Message msg = handlerMessage.obtainMessage();
							Bundle bundle = new Bundle();
							bundle.putString("notice", getString(R.string.setting_progress_message_login_noinfo));
							msg.setData(bundle);
							msg.what = HANDLER_MESSAGE_NOTICE;
							handlerMessage.sendMessage(msg);
						}
					} else { // 登录失败
						Message msg = handlerMessage.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putString("notice", result.get(UserLogin.ERR_MSG));
						msg.setData(bundle);
						msg.what = HANDLER_MESSAGE_NOTICE;
						handlerMessage.sendMessage(msg);
					}
				} else { // 无法请求
					Message msg = handlerMessage.obtainMessage();
					Bundle bundle = new Bundle();
					bundle.putString("notice", getString(R.string.setting_progress_message_norequest));
					msg.setData(bundle);
					msg.what = HANDLER_MESSAGE_NOTICE;
					handlerMessage.sendMessage(msg);
				}
				closeProgress();
			}
		}.start();
	}

	/**
	 * 线程消息处理
	 */
	Handler handlerMessage = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HANDLER_MESSAGE_NOTICE: // 弹出信息
				Bundle bundle = msg.getData();
				String notice = bundle.getString("notice");
				showToastLong(notice);
				break;
			case HANDLER_MESSAGE_JUMP: // 成功跳转
				startActivity(new Intent(mContext, SettingLogined.class));
				SettingAccount.this.finish();
				break;
			}
		}
	};

}