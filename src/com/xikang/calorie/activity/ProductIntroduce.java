/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: ProductIntroduce.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xikang.calorie.common.Constants;
import com.xikang.calorie.view.CustomGallery;

public class ProductIntroduce extends Activity {

	private CustomGallery glPI;

	private int[] picAd = new int[] { R.drawable.product_pic_1, R.drawable.product_pic_2, R.drawable.product_pic_3 };
	private int[] titleAd = new int[] { R.string.Product_Title_1, R.string.Product_Title_2, R.string.Product_Title_3 };
	private int[] contentAd = new int[] { R.string.Product_Content_1, R.string.Product_Content_2,
			R.string.Product_Content_3 };

	private int[] scrollId = new int[] { R.id.ivPiScroll1, R.id.ivPiScroll2, R.id.ivPiScroll3 };

	private Context mContext;

	private LayoutInflater mInflater;

	private ImageButton ibProductClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_product_introduce);
		mContext = ProductIntroduce.this;
		init();

	}

	private void init() {

		ibProductClose = (ImageButton) findViewById(R.id.ibProductClose);
		ibProductClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ProductIntroduce.this.finish();
			}
		});

		glPI = (CustomGallery) findViewById(R.id.glPI);

		glPI.setAdapter(new ProductIntroduceAdapter());

		glPI.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				ImageView iv = (ImageView) ProductIntroduce.this.findViewById(scrollId[position]);
				iv.setImageResource(R.drawable.product_scroll_on);
				changeScroll(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void changeScroll(int index) {
		int len = scrollId.length;
		for (int i = 0; i < len; i++) {
			if (i != index) {
				ImageView iv = (ImageView) ProductIntroduce.this.findViewById(scrollId[i]);
				iv.setImageResource(R.drawable.product_scroll_off);
			}
		}

	}

	static class Holder {
		ImageView ivProductAd;
		TextView tvProductTitle;
		TextView tvProductContent;
		Button btnProductBinding;
	}

	class ProductIntroduceAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return picAd.length;
		}

		@Override
		public Object getItem(int position) {
			return picAd;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final Holder holder;

			if (convertView == null) {

				mInflater = LayoutInflater.from(mContext);
				convertView = mInflater.inflate(R.layout.layout_product_element, null);
				holder = new Holder();
				holder.ivProductAd = (ImageView) convertView.findViewById(R.id.ivProductAd);
				holder.tvProductTitle = (TextView) convertView.findViewById(R.id.tvProductTitle);
				holder.tvProductContent = (TextView) convertView.findViewById(R.id.tvProductContent);
				holder.btnProductBinding = (Button) convertView.findViewById(R.id.btnProductBinding);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			if (position == 0 && !Constants.IS_LOGIN) {
				holder.btnProductBinding.setVisibility(View.VISIBLE);
				holder.btnProductBinding.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(ProductIntroduce.this, SettingAccount.class));
						ProductIntroduce.this.finish();
					}
				});
			}
			holder.ivProductAd.setImageResource(picAd[position]);
			holder.tvProductTitle.setText(titleAd[position]);
			holder.tvProductContent.setText(contentAd[position]);

			return convertView;

		}

	}

}
