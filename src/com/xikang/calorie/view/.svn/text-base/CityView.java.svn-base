/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: CityView.java
 * --------------------------------------------------
 * 开发环境: JDK1.6 
 * --------------------------------------------------
 * 修订履历  YYYY/MM/DD  REV.  备注
 *         2011/08/11  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xikang.calorie.activity.R;
import com.xikang.calorie.db.CityService;
import com.xikang.calorie.domain.City;

/**
 * 
 * 自定义城市控件
 * 
 * 
 * <pre>
 * 
 * 2011/07/18, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * 修订履历：2011/08/11 曲磊 样式逻辑修改
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class CityView extends LinearLayout {

	private LayoutInflater mInflater;
	private ListView listView;
	private CityListAdapter mCityListAdapter;

	private CityService mCityService;
	
	private List<City> cityList = new ArrayList<City>();

	private List<ImageView> ivList = new ArrayList<ImageView>();
	private Map<City, Boolean> map = new HashMap<City, Boolean>();
	
	private String parentIdCurrent;
	
	private LinearLayout btnBack;
	
	public CityView(Context context, CityService cityService) {
		super(context);
		mCityService = cityService;
		mInflater = LayoutInflater.from(context);
		LinearLayout mLinearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_view_city, null);
		this.addView(mLinearLayout);
		init();

	}

	private void init() {
		mCityListAdapter = new CityListAdapter();
		
		btnBack = (LinearLayout) this.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				City city = mCityService.getCity(parentIdCurrent);
				setListViewData(city.getParentId());
				mCityListAdapter.notifyDataSetChanged();
				ivList.clear();
			}
		});

		listView = (ListView) this.findViewById(R.id.listCityView);
		listView.setAdapter(mCityListAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				City city = cityList.get(position);

				if (city.getList() == null) {
					clearMarkImage();
					ListHolder holder = (ListHolder) view.getTag();
					holder.image.setImageResource(android.R.drawable.checkbox_on_background);
					addRadioMark(city);
				} else {
					setListViewData(city.getCode());
					mCityListAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	public City getSelectedCity() {
		City city = null;
		for (Map.Entry<City, Boolean> entry : map.entrySet()) {
			if (entry.getValue()) {
				city = entry.getKey();
				break;
			}
		}
		return city;
	}

	/**
	 * 更新列表数据
	 * 
	 * @param parentId
	 */
	public void setListViewData(String parentId) {
		if(parentId.equals("0")){
			btnBack.setVisibility(View.GONE);
		}else{
			btnBack.setVisibility(View.VISIBLE);
		}
		
		cityList.clear();
		parentIdCurrent = parentId;
		cityList = mCityService.getCityList(parentId);
		int lenGroup = cityList.size();
		for (int i = 0; i < lenGroup; i++) {
			City mCity = cityList.get(i);
			cityList.get(i).setList(mCityService.getCityList(mCity.getCode()));
		}
		mCityListAdapter.notifyDataSetChanged();
	}

	public void selectCity(City city) {
		addRadioMark(city);
		listView.setSelection(findCityIndex(city));
	}

	private int findCityIndex(City city) {
		int i = 0;
		for (City iCity : cityList) {
			if (iCity.getCode().equals(city.getCode())) {
				return i;
			} else {
				i++;
			}

		}
		return i;
	}

	private static class ListHolder {
		TextView text;
		ImageView image;
	}

	/**
	 * 查找选择标记图片
	 * 
	 * @param city
	 * @return
	 */
	private int findRadioMark(City city) {
		for (Map.Entry<City, Boolean> entry : map.entrySet()) {
			if (entry.getKey().getCode().equals(city.getCode())) {
				if (entry.getValue()) {
					return android.R.drawable.checkbox_on_background;
				} else {
					return android.R.drawable.checkbox_off_background;
				}
			}
		}
		return android.R.drawable.checkbox_off_background;
	}

	/**
	 * 标记选择的城市
	 * 
	 * @param city
	 */
	private void addRadioMark(City city) {
		map.put(city, true);
		for (Map.Entry<City, Boolean> entry : map.entrySet()) {
			if (!entry.getKey().getCode().equals(city.getCode())) {
				entry.setValue(false);
			}
		}
	}

	/**
	 * 清楚未选择
	 */
	private void clearMarkImage() {
		for (ImageView list : ivList) {
			list.setImageResource(android.R.drawable.checkbox_off_background);
		}
	}

	class CityListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return cityList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return cityList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {

			ListHolder holder;
			City city = cityList.get(position);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.layout_view_city_element, null);
				holder = new ListHolder();
				holder.text = (TextView) convertView.findViewById(R.id.tvCityView);
				holder.image = (ImageView) convertView.findViewById(R.id.ivCityView);
				convertView.setTag(holder);
			} else {
				holder = (ListHolder) convertView.getTag();
			}

			holder.text.setText(city.getName());

			if (city.getList() == null) {
				ivList.add(holder.image);
				holder.image.setImageResource(findRadioMark(city));
			} else {
				ivList.remove(holder.image);
				holder.image.setImageResource(R.drawable.main_setting_layout_right);
			}

			return convertView;
		}
	}

}
