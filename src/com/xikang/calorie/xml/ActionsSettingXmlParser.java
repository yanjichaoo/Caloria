package com.xikang.calorie.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.xikang.calorie.xml.XmlConstants.ActionSettingGetter;


public class ActionsSettingXmlParser extends BaseXmlParser {

	public ActionsSettingXmlParser(Context context) {
		super(context);

	}

	public String buildActionSettingXML(String personId, String lineId, String status) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);

		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, ActionSettingGetter.FUNCTION_NAME);
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, personId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, ActionSettingGetter.LINE_ID, lineId);
		serializer.attribute(null, XmlConstants.STATUS, status);
		serializer.attribute(null, XmlConstants.VERSION, version);
		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		return arrayOutputStream.toString();

	}

	public boolean parseXml(String writeBack) throws Exception {
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();

		int status = 0;
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			
			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
			}
			type = parser.next();
		}
		if(status==0){
			return false;
		}else{
			return true;
		}
	}

}
