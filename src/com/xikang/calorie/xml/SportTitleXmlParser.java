/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: HistoryXmlParser.java
 * --------------------------------------------------
 * 开发环境: JDK1.6
 * --------------------------------------------------
 * 修订履历  2011/06/15  REV.  备注
 *         2011/06/15  1.00  初版发行
 * --------------------------------------------------
 * (C) Copyright XIKANG CORPORATION 2011 All Rights Reserved.
 */
package com.xikang.calorie.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.xml.XmlConstants.SportTitleGetter;

/**
 * 
 * 运动称号接口XML解析类
 * 
 * 
 * <pre>
 * 
 * 2011/07/07, 代俊义
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 
 * </pre>
 * 
 * @author 代俊义
 * @version 1.00
 */
public class SportTitleXmlParser extends BaseXmlParser {
	public SportTitleXmlParser(Context context) {
		super(context);
	}

	/**
	 * 构建获取运动称号xml
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildSummaryXML() throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, "getSportTitle_iPhone");
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, SportTitleGetter.COUNTRY_CODE, "CN");
		serializer.attribute(null, SportTitleGetter.LANGUAGE, "ZH_CN");
		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		return arrayOutputStream.toString();
	}

	/**
	 * 解析返回的运动称号xml
	 * 
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> parseSummaryXml(String writeBack) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					if ("0".equals(parser.getAttributeValue(null, XmlConstants.STATUS))) {
						return list;
					}
				}
				if (parser.getName().contains(SportTitleGetter.LEVEL)) {
					Map<String, String> level = new HashMap<String, String>();
					level.put(SportTitleGetter.VALUE, parser.getAttributeValue(null, SportTitleGetter.VALUE));
					level.put(SportTitleGetter.NAME, parser.getAttributeValue(null, SportTitleGetter.NAME));
					list.add(level);
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			type = parser.next();
		}
		is.close();
		return list;
	}
}
