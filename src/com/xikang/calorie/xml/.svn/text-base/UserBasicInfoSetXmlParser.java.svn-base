/* ==================================================
 * 产品名: 路里消耗管理
 * 文件名: SetUserBasicInfoXml.java
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.domain.ProfileInfo;
import com.xikang.calorie.xml.XmlConstants.UserBasicInfoSetter;

/**
 * 
 * 设置个人信息数据接口XML解析类
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：YYYY/MM/DD 修订者  修订内容
 * 2011/06/19 曲磊 修改接口的数据参数
 * 2011/08/10 曲磊 修改结构
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class UserBasicInfoSetXmlParser extends BaseXmlParser {

	public UserBasicInfoSetXmlParser(Context context) {
		super(context);
	}

	/**
	 * 请求XML生成
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildXML(String personId, ProfileInfo profileInfo) throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);

		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, UserBasicInfoSetter.ID, UserBasicInfoSetter.FUNCTION_NAME);
		serializer.attribute(null, UserBasicInfoSetter.PERSON_ID, personId);
		serializer.attribute(null, UserBasicInfoSetter.IPHONE_ID, iphone_id);
		serializer.attribute(null, UserBasicInfoSetter.BIRTHDAY, profileInfo.getBirthday());
		serializer.attribute(null, UserBasicInfoSetter.GENDER, profileInfo.getSex());
		serializer.attribute(null, UserBasicInfoSetter.HEIGHT, profileInfo.getHeight());
		serializer.attribute(null, UserBasicInfoSetter.WEIGHT, profileInfo.getWeight());
		serializer.attribute(null, UserBasicInfoSetter.TARGETWEIGHT, profileInfo.getTargetWeight());
		serializer.attribute(null, UserBasicInfoSetter.CITYCODE, profileInfo.getCityCode());
		serializer.attribute(null, UserBasicInfoSetter.VITALITY, profileInfo.getVitality());
		serializer.attribute(null, UserBasicInfoSetter.VERSION, version);

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
	public String parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		String status = null;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {

			case XmlPullParser.START_DOCUMENT:

				break;

			case XmlPullParser.START_TAG:

				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = parser.getAttributeValue(null, XmlConstants.STATUS);
				}

				break;

			case XmlPullParser.END_TAG:
				break;

			}
			type = parser.next();
		}
		is.close();

		return status;
	}

}
