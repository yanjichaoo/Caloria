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
import com.xikang.calorie.domain.Line;
import com.xikang.calorie.xml.XmlConstants.ActionsGetLinesListGetter;

public class ActionsGetLinesListXmlParser extends BaseXmlParser{

	public ActionsGetLinesListXmlParser(Context context) {
		super(context);
		
	}
	
	public String buildActionGetLinesListXML(String personId) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, ActionsGetLinesListGetter.FUNCTION_NAME);
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, personId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		return arrayOutputStream.toString();
	}
	
	public List<Line> parseXml(String writeBack) throws Exception{
		System.out.println("##"+writeBack);
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();
		
		int status = 0;
		
		
		List<Line> lines=null;
		Line line=null;
		
	
		while (type != XmlPullParser.END_DOCUMENT) {
			
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				
				lines=new ArrayList<Line>();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
					
				}
				if (status == 1) {
				
					
					if (ActionsGetLinesListGetter.ROUTE.equals(parser.getName())) {
						
						line=new Line();
					}
					if(XmlConstants.ID.equals(parser.getName())){
						line.setId(parser.nextText());
					}
					if(ActionsGetLinesListGetter.NAME.equals(parser.getName())){
						line.setName(parser.nextText());
					}
					

				}
				break;

			case XmlPullParser.END_TAG:
				if (ActionsGetLinesListGetter.ROUTE.equals(parser.getName())) {
					lines.add(line);
				}
				
				break;

			}
			type = parser.next();
		}
		is.close();
		return lines;
		
	}
}
