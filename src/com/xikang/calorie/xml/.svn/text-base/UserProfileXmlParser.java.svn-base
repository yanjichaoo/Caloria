/* ==================================================
 * 产品名: 卡路里消耗管理
 * 文件名: UserProfileXmlParser.java
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

import com.xikang.calorie.domain.UserInfo;
import com.xikang.calorie.xml.XmlConstants.UserProfileGetter;
/**
 * 
 * 账户信息数据接口XML解析类
 * 
 * 
 * <pre>
 * 
 * 2011/06/13, 曲磊
 * 
 * 修订履历：2011/06/14 曲磊 接口调试修改成功
 * 修订履历：2011/08/10 曲磊 修改结构
 * 
 * </pre>
 * 
 * @author 曲磊
 * @version 1.00
 */
public class UserProfileXmlParser extends BaseXmlParser{
	
	
	public UserProfileXmlParser(Context context) {
		super(context);
	}
	
	/**
	 * 请求XML生成
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String buildXML(String personId) throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");

		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);

		serializer.startTag(null, XmlConstants.FUNCTION);
		serializer.attribute(null, XmlConstants.ID, UserProfileGetter.FUNCTION_NAME);
		serializer.attribute(null, UserProfileGetter.IPHONE_ID, iphone_id);
		serializer.attribute(null, UserProfileGetter.PERSON_ID, personId);
		serializer.attribute(null, UserProfileGetter.VERSION, version);
		serializer.endTag(null, XmlConstants.FUNCTION);

		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);

		serializer.endDocument();
		dataOutputStream.close();

		return arrayOutputStream.toString();
	}
	/**
	 * 应答XML解析
	 * @param writeBack
	 * @return
	 * @throws Exception
	 */
	public UserInfo parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		UserInfo userInfo = null;
		int status = 0;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {

			case XmlPullParser.START_DOCUMENT:

				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					//状态
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if(status == 1){
					if(UserProfileGetter.USERPROFILE.equals(parser.getName())){
						//内容信息
						userInfo = new UserInfo();
						userInfo.setEmail(parser.getAttributeValue(null, UserProfileGetter.EMAIL));
						userInfo.setPhoneNum(parser.getAttributeValue(null, UserProfileGetter.PHONENUM));
						userInfo.setWatchId(parser.getAttributeValue(null, UserProfileGetter.WATCH_ID));
						userInfo.setUserName(parser.getAttributeValue(null, UserProfileGetter.USERNAME));
						userInfo.setSummaryTimerEnabled(parser.getAttributeValue(null, UserProfileGetter.SUMMARYTIMERENABLED));
						userInfo.setArticleTimerEnabled(parser.getAttributeValue(null, UserProfileGetter.ARTICLETIMERENABLED));
						userInfo.setTargetAlertTimerEnabled(parser.getAttributeValue(null, UserProfileGetter.TARGETALERTTIMERENABLED));
					}
				}
	
				
	
				break;

			case XmlPullParser.END_TAG:
				break;

			}
			type = parser.next();
		}
		is.close();

		return userInfo;
	}

}
