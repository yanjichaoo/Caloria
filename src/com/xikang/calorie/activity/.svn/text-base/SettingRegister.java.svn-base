/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: SettingRegister.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.db.DB_ConnectionPool;
import com.xikang.calorie.db.UserInfoService;
import com.xikang.calorie.domain.ProfileInfo;
import com.xikang.calorie.domain.UserInfo;
import com.xikang.calorie.xml.XmlConstants;
import com.xikang.calorie.xml.XmlConstants.UserLogin;
import com.xikang.calorie.xml.XmlConstants.UserRegister;

/**
 * 
 * 用户注册界面
 * 
 * 
 * <pre>
 * 
 * 2011/06/20, 曲磊
 * 
 * 修订履历：2011/06/20 曲磊  修改数据功能添加
 * 修订履历：2011/08/10 曲磊  优化程序
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class SettingRegister extends BaseActivity implements OnClickListener {
	// 文本控件
	private EditText etSREmail;
	private EditText etSRPwd;
	private EditText etSRPwdConfirm;
	private EditText etSRWatchId;
	private EditText etSRUserName;
	private EditText etSRPhone;

	private UserInfoService mUserInfoService;

	private static final int HANDLER_MESSAGE_NOTICE = 8001;
	private static final int Handler_MESSAGE_JUMP = 8002;

	// 注册按钮
	private LinearLayout lySRRegister;

	private TextView tvSRProvision;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_register);
		mUserInfoService = new UserInfoService(DB_ConnectionPool.getInstance(mContext));
		init();
		loading();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		showTitle(getString(R.string.setting_layout_register));
		showLeftButton(getString(R.string.common_back), new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SettingRegister.this.finish();
			}
		});

		lySRRegister = (LinearLayout) findViewById(R.id.lySRRegister);
		lySRRegister.setOnClickListener(this);

		etSREmail = (EditText) findViewById(R.id.etSREmail);
		etSRPwd = (EditText) findViewById(R.id.etSRPwd);
		etSRPwdConfirm = (EditText) findViewById(R.id.etSRPwdConfirm);
		etSRWatchId = (EditText) findViewById(R.id.etSRWatchId);
		etSRUserName = (EditText) findViewById(R.id.etSRUserName);
		etSRPhone = (EditText) findViewById(R.id.etSRPhone);

		tvSRProvision = (TextView) findViewById(R.id.tvSRProvision);
		tvSRProvision.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showResult(getString(R.string.setting_register_message_4),
						getString(R.string.setting_register_message_5), null);
			}
		});
	}

	/**
	 * 加载数据
	 */
	private void loading() {
		Bundle bundle = getIntent().getExtras();
		String email = bundle.getString("Email");
		String Watch = bundle.getString("Watch");
		if (!email.equals("")) {
			etSREmail.setText(email);
		}

		if (!Watch.equals("")) {
			etSRWatchId.setText(Watch);
		}
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.lySRRegister:
			registerUser();
			break;
		}
	}

	/**
	 * 判断EditText是否为空
	 */
	private boolean isEditEmpty(EditText et) {
		if (et.getText().toString().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 提交注册
	 */
	private void registerUser() {
		if (!etSRPwd.getText().toString().equals(etSRPwdConfirm.getText().toString())) {
			showToastLong(getString(R.string.setting_progress_message_pwd_notmatch));
			return;
		}

		if (isEditEmpty(etSREmail) || isEditEmpty(etSRPwd) || isEditEmpty(etSRPwdConfirm) || isEditEmpty(etSRWatchId)
				|| isEditEmpty(etSRUserName)) {
			showToastLong(getString(R.string.setting_progress_message_userinfo_asymmetry));
			return;
		}
		showProgress(getString(R.string.setting_progress_message_register));

		new Thread() {
			public void run() {
				UserInfo mUserInfo = new UserInfo();
				mUserInfo.setEmail(etSREmail.getText().toString());
				mUserInfo.setPwd(etSRPwd.getText().toString());
				mUserInfo.setWatchId(etSRWatchId.getText().toString());
				mUserInfo.setUserName(etSRUserName.getText().toString());
				mUserInfo.setPhoneNum(etSRPhone.getText().toString());
				Map<String, String> map = mXmlope.registerUser(mUserInfo);
				if (map != null) {
					int status = Integer.valueOf(map.get(XmlConstants.STATUS));
					if (status == 1) { // 注册成功
						Constants.IS_LOGIN = true;
						Constants.CURRENT_USER = mUserInfo;

						mUserInfo.setPersonId(map.get(UserRegister.PERSON_ID));
						mUserInfoService.saveUserInfo(mUserInfo);
						Constants.CURRENT_PROFILE = ProfileInfo.getInstance(mXmlope,
								Constants.CURRENT_USER.getPersonId());
						Message msg = handlerMessage.obtainMessage();
						msg.what = Handler_MESSAGE_JUMP;
						handlerMessage.sendMessage(msg);

					} else { // 注册失败
						Message msg = handlerMessage.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putString("notice", map.get(UserLogin.ERR_MSG));
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
			case Handler_MESSAGE_JUMP: // 成功跳转
				Intent intent = SettingRegister.this.getIntent();
				setResult(Constants.PAGE_SETTING_ACCOUNT, intent);
				SettingRegister.this.finish();
				break;
			}
		}
	};

}
