/* ==================================================
 * 产品名: 路里消耗管理
 * 文件名: BaseXmlParser.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.xml;

import com.xikang.calorie.common.Util;

import android.content.Context;


/**
 * 
 * 基础XML解析类
 * 
 * <pre>
 * 
 * 2011/08/10, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class BaseXmlParser {
	
	public static final String DEVICE_TYPE = "Android";
	
	protected Context mContext;
	protected String version;
	protected String iphone_id;

	public BaseXmlParser(Context context){
		mContext = context;
		version = Util.getPackageVersion(context);
		iphone_id  = Util.getAndroidDeviceId(context);
	}

}
