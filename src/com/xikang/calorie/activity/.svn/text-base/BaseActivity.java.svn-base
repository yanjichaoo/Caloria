/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: BaseActivity.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/08/09  REV.  备注
 *         2011/08/09  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import com.xikang.calorie.xml.XmlOpe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 基础Activity
 * 
 * 
 * <pre>
 * 
 * 2011/08/09, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */

public class BaseActivity extends Activity {

	protected static final int LOADING_SCUESS = 9001;
	protected static final int LOADING_FAILED = 9002;

	private ProgressDialog processDialog;

	public XmlOpe mXmlope;
	
	protected Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this.getApplicationContext();
		mXmlope = new XmlOpe(mContext);

	}

	/**
	 * 标题
	 * 
	 * @param title
	 */
	public void showTitle(String title) {
		TextView tvTBContent = (TextView) findViewById(R.id.tvTBContent);
		tvTBContent.setText(title);
	}

	/**
	 * 左按钮
	 * 
	 * @param leftText
	 * @param listener
	 */
	public void showLeftButton(String leftText, OnClickListener listener) {
		Button btnTBLeft = (Button) findViewById(R.id.btnTBLeft);
		btnTBLeft.setVisibility(View.VISIBLE);
		btnTBLeft.setText(leftText);
		btnTBLeft.setOnClickListener(listener);
	}

	/**
	 * 右按钮
	 * 
	 * @param rightText
	 * @param listener
	 */
	public void showRightButton(String rightText, OnClickListener listener) {
		Button btnTBRight = (Button) findViewById(R.id.btnTBRight);
		btnTBRight.setVisibility(View.VISIBLE);
		btnTBRight.setText(rightText);
		btnTBRight.setOnClickListener(listener);
	}

	/**
	 * 弹出进度框
	 * 
	 * @param message
	 */
	public void showProgress(final String message) {
		BaseActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				processDialog = new ProgressDialog(BaseActivity.this);
				processDialog.setMessage(message);
				processDialog.setIndeterminate(true);
//				processDialog.setCancelable(false);
				processDialog.show();

			}
		});

	}

	/**
	 * 关闭进度框
	 * 
	 * @param message
	 */
	public void closeProgress() {
		BaseActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (processDialog != null && processDialog.isShowing()) {
					processDialog.dismiss();
				}
			}
		});
	}

	/**
	 * 提示信息
	 * 
	 * @param message
	 */
	public void showToastShort(String message) {
		Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 提示信息
	 * 
	 * @param message
	 */
	public void showToastLong(String message) {
		Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示结果
	 * 
	 * @param message
	 */
	public void showResult(final String message) {
		showResult(null, message, null);
	}

	/**
	 * 显示结果
	 * 
	 * @param message
	 */
	public void showResult(final String title, final String message) {
		showResult(title, message, null);
	}

	/**
	 * 显示结果
	 * 
	 * @param message
	 */
	public void showResult(final String title, final String message, final DialogInterface.OnClickListener listener) {

		BaseActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				new AlertDialog.Builder(BaseActivity.this).setTitle(title).setMessage(message)
						.setPositiveButton(R.string.common_ok, listener).show();
			}
		});
	}

	/**
	 * 显示结果
	 * 
	 * @param message
	 */
	public void showResult(final String title, final String message,
			final DialogInterface.OnClickListener listenerPositive,
			final DialogInterface.OnClickListener listenerNegative) {

		BaseActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				new AlertDialog.Builder(BaseActivity.this).setTitle(title).setMessage(message)
						.setPositiveButton(R.string.common_ok, listenerPositive)
						.setNegativeButton(R.string.common_cancel, listenerNegative).show();
			}
		});
	}

}
