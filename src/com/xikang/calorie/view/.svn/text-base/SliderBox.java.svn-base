package com.xikang.calorie.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xikang.calorie.activity.R;

/**
 * 
 * 自定义滑块控件
 * 
 * 
 * <pre>
 * 
 * 2011/08/16, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */

public class SliderBox extends FrameLayout {

	private boolean isCheck = true;
	private LinearLayout layoutSlider;
	private LayoutParams frameLayoutParams;
	private FrameLayout mFrameLayout;

	private TextView tvCheckOn;
	private TextView tvCheckOff;
	private TextView tvSlider;
	
	private Context mContext;

	public SliderBox(Context context) {
		this(context, null);

	}

	public SliderBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater mInflater = LayoutInflater.from(context);
		mFrameLayout = (FrameLayout) mInflater.inflate(R.layout.layout_view_sliderbox, null);
		addView(mFrameLayout);
		init(attrs);
	}

	public void init(AttributeSet attrs) {
		
		TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.SliderBox);
		
		tvCheckOn = (TextView) findViewById(R.id.tvCheckOn);
		tvCheckOff = (TextView) findViewById(R.id.tvCheckOff);
		tvSlider = (TextView) findViewById(R.id.tvSlider);
		
		tvCheckOn.setText(array.getString(R.styleable.SliderBox_sliderCheckOn));
		tvCheckOff.setText(array.getString(R.styleable.SliderBox_sliderCheckOff));
		isCheck = array.getBoolean(R.styleable.SliderBox_sliderIsCheck, true);
		
		float textSize = (float) array.getDimension(R.styleable.SliderBox_sliderTextSize, 12);
		
		tvCheckOn.setTextSize(textSize);
		tvCheckOff.setTextSize(textSize);
		tvSlider.setTextSize(textSize);

	
		layoutSlider = (LinearLayout) this.findViewById(R.id.btnSlider);
		frameLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setSliderCheck(!isCheck);
				if (mOnSliderBoxCheckListener != null) {
					mOnSliderBoxCheckListener.onChecked();
				}

			}

		});
		invaliView(isCheck);
		
		array.recycle();
	}
	
	private void invaliView(boolean checked){
		if (checked) {
			tvSlider.setText(tvCheckOn.getText());
			frameLayoutParams.gravity = Gravity.LEFT;
		} else {
			tvSlider.setText(tvCheckOff.getText());
			frameLayoutParams.gravity = Gravity.RIGHT;
		}
		layoutSlider.setLayoutParams(frameLayoutParams);
	}

	public void setSliderCheck(boolean checked) {
		isCheck = checked;
		invaliView(isCheck);
	}

	public boolean getSliderCheck() {
		return isCheck;
	}
	
	private OnSliderBoxCheckListener mOnSliderBoxCheckListener;
	
	public interface OnSliderBoxCheckListener {
		void onChecked();
	}
	
	public void setOnSliderBoxCheckListener(OnSliderBoxCheckListener onSliderBoxCheckListener) {
		this.mOnSliderBoxCheckListener = onSliderBoxCheckListener;
	}
}
