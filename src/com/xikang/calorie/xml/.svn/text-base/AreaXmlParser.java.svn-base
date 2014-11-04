/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: AreaXmlParser.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/13  REV.  备注
 *         2011/06/13  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.domain.City;
import com.xikang.calorie.xml.XmlConstants.AreaGetter;

/**
 * 
 * 地区数据接口XML解析类
 * 
 * <pre>
 * 
 * 2011/06/16, 曲磊
 * 
 * 修订履历：2011/08/10 曲磊 修改结构
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class AreaXmlParser extends BaseXmlParser {

	public AreaXmlParser(Context context) {
		super(context);
	}

	/**
	 * 请求XML生成
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildXML() throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);

		serializer.startTag(null, XmlConstants.FUNCTION);
		serializer.attribute(null, XmlConstants.ID, AreaGetter.FUNCTION_NAME);
		serializer.attribute(null, AreaGetter.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, AreaGetter.IPHONE_ID, iphone_id);
		serializer.attribute(null, AreaGetter.COUNTRYCODE, "CN");
		serializer.attribute(null, AreaGetter.LANGUAGE, "ZH_CN");
		serializer.attribute(null, AreaGetter.VERSION, version);
		serializer.endTag(null, XmlConstants.FUNCTION);

		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		return arrayOutputStream.toString();
	}

	/**
	 * 应答XML解析
	 * 
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public static List<City> parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();

	
		int status = 0;
		List<City> cityList = null;
		String level = "0";
		String parentId = "0";
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {

			case XmlPullParser.START_DOCUMENT:
				City mCity;
				cityList = new ArrayList<City>();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					// 状态
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if (status == 1) {
					if (parser.getName().contains(AreaGetter.NATION)) {
						mCity = new City();
						mCity.setCode(parser.getAttributeValue(null, AreaGetter.CODE));
						mCity.setName(parser.getAttributeValue(null, AreaGetter.NAME));
						mCity.setParentId(parentId);
						cityList.add(mCity);
						parentId = parser.getAttributeValue(null, AreaGetter.CODE);
						level = parentId;
					}
					if (parser.getName().contains(AreaGetter.PROVINCE)) {
						mCity = new City();
						mCity.setCode(parser.getAttributeValue(null, AreaGetter.CODE));
						mCity.setName(parser.getAttributeValue(null, AreaGetter.NAME));
						mCity.setParentId(parentId);
						cityList.add(mCity);
						parentId = parser.getAttributeValue(null, AreaGetter.CODE);
					}
					if (parser.getName().contains(AreaGetter.CITY)) {
						mCity = new City();
						mCity.setCode(parser.getAttributeValue(null, AreaGetter.CODE));
						mCity.setName(parser.getAttributeValue(null, AreaGetter.NAME));
						mCity.setParentId(parentId);
						cityList.add(mCity);
					}
				}
				break;

			case XmlPullParser.END_TAG:
				if(parser.getName().contains(AreaGetter.PROVINCE)){
					parentId = level;
				}
				if (parser.getName().contains(AreaGetter.NATION)) {
					parentId = "0";
				}
				break;
			}
			type = parser.next();
		}
		is.close();
		return cityList;
	}
}