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

import com.xikang.calorie.domain.ActionLines;
import com.xikang.calorie.domain.ActionLines.MyPoint;
import com.xikang.calorie.domain.Line;
import com.xikang.calorie.xml.XmlConstants.ActionGetSelectRouteGetter;

public class ActionsGetSelectRouteXmlParser extends BaseXmlParser{

	public ActionsGetSelectRouteXmlParser(Context context) {
		super(context);
		
	}
	public String buildActionsGetSelectRouteXML(String personId, String lineId) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, ActionGetSelectRouteGetter.FUNCTION_NAME);
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, personId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, ActionGetSelectRouteGetter.LINE_ID, lineId);
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		
		return arrayOutputStream.toString();

	}
	public Line parseXml(String writeBack) throws Exception{
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();

		int status = 0;
		
		ActionLines mActionLines=new ActionLines();
		Line line=null;
		List<MyPoint> points=null;
		MyPoint point=null;
		
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				line=new Line();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					line.setStatus(parser.getAttributeValue(null, XmlConstants.STATUS));
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if (status == 1) {
					if(ActionGetSelectRouteGetter.CENTER_LX.equals(parser.getName())){
						line.setCenter_lX(parser.nextText());
					}
					if(ActionGetSelectRouteGetter.CENTER_LY.equals(parser.getName())){
						line.setCenter_lY(parser.nextText());
					}
					
					if (ActionGetSelectRouteGetter.SPAN_LONGIDELTA.equals(parser.getName())) {
						line.setSpan_longiDelta(parser.nextText());
					}

					if (ActionGetSelectRouteGetter.SPAN_LANGDELTA.equals(parser.getName())) {
						line.setSpan_langtDelta(parser.nextText());
					}
					
					
					if (ActionGetSelectRouteGetter.ROUTE.equals(parser.getName())) {
						
						points=new ArrayList<MyPoint>();
					}
					if(ActionGetSelectRouteGetter.NAME.equals(parser.getName())){
						line.setName(parser.nextText());
					}
					if(ActionGetSelectRouteGetter.DIST.equals(parser.getName())){
						line.setDist(parser.nextText());
					}
					if(ActionGetSelectRouteGetter.NOTICE.equals(parser.getName())){
						line.setNotice(parser.nextText());
					}
					
					if (ActionGetSelectRouteGetter.POINTS.equals(parser.getName())) {
						point=mActionLines.new MyPoint();
					}
					
					if(ActionGetSelectRouteGetter.LONGI.equals(parser.getName())){
						point.setLongi((int)(Double.parseDouble(parser.nextText())*1000000));
					}
					if(ActionGetSelectRouteGetter.LANGT.equals(parser.getName())){
						point.setLangt((int)(Double.parseDouble(parser.nextText())*1000000));
					}

				}
				break;

			case XmlPullParser.END_TAG:
				if (ActionGetSelectRouteGetter.POINTS.equals(parser.getName())) {
					points.add(point);
				}
				if (ActionGetSelectRouteGetter.ROUTE.equals(parser.getName())) {
					line.setPoints(points);
				}
				
				break;

			}
			type = parser.next();
		}
		is.close();
		return line;
	}
	
}
