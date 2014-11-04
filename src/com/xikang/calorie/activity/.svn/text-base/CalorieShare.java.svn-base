/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: CalorieShare.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/21  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xikang.calorie.common.OAuthConstant;

/**
 * 
 * 微博分享窗体
 * 
 * 
 * <pre>
 * 
 * 2011/06/21, 张荣
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 张荣
 * @version 1.00
 */
public class CalorieShare extends Activity implements OnClickListener {
	private TextView mTvTBContent;
	private TextView mTvShareContent;
	private Button mBtnOk;
	private Button mBtnCancel;
	private Resources mRes;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_weibo_share);

		// 页标题设置
		mTvTBContent = (TextView) findViewById(R.id.tvTBContent);
		mTvTBContent.setText(R.string.weibo_title);

		mBtnOk = (Button) findViewById(R.id.btnOk);
		mBtnCancel = (Button) findViewById(R.id.btnCancel);
		mTvShareContent = (TextView) findViewById(R.id.weibo_share_content);
		mRes = getResources();

		SharedPreferences pref = getSharedPreferences("weibo_shared", Context.MODE_PRIVATE);

		if ((pref.getString("Token", null) == null) || (pref.getString("TokenSecret", null) == null)) {

			Uri uri = this.getIntent().getData();
			try {
				RequestToken requestToken = OAuthConstant.getInstance().getRequestToken();
				AccessToken accessToken = requestToken.getAccessToken(uri.getQueryParameter("oauth_verifier"));
				OAuthConstant.getInstance().setAccessToken(accessToken);

				SharedPreferences.Editor editor = pref.edit();

				editor.putString("Token", OAuthConstant.getInstance().getToken());
				editor.putString("TokenSecret", OAuthConstant.getInstance().getTokenSecret());
				editor.commit();

			} catch (WeiboException e) {
				e.printStackTrace();
			}
		}

		mBtnOk.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);

		String sharedContent = pref.getString("SharedContent", null);
		mTvShareContent.setText(sharedContent);
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();

		switch (viewId) {
		case R.id.btnOk:
			Time t = new Time();
			t.setToNow();
			String timeStamp = String.valueOf(t.year).concat("-").concat(String.valueOf(t.month)).concat("-")
					.concat(String.valueOf(t.monthDay)).concat(" ").concat(String.valueOf(t.hour)).concat(":")
					.concat(String.valueOf(t.minute)).concat(":").concat(String.valueOf(t.second));
			Weibo weibo = OAuthConstant.getInstance().getWeibo();
			weibo.setToken(OAuthConstant.getInstance().getToken(), OAuthConstant.getInstance().getTokenSecret());
			try {
				weibo.updateStatus(mTvShareContent.getText().toString().concat("\n\n").concat(timeStamp));
				Toast.makeText(this, mRes.getString(R.string.share_message_1), Toast.LENGTH_LONG).show(); 
				weibo.endSession();
			} catch (WeiboException e) {
				e.printStackTrace();
				Toast.makeText(this, mRes.getString(R.string.share_message_2), Toast.LENGTH_LONG).show(); 
				return;
			}
			this.finish();

			break;
		case R.id.btnCancel:
			this.finish();
			break;
		}
	}
}
