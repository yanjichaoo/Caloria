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
import com.xikang.calorie.xml.XmlConstants.ActionsGetLinesGetter;

public class ActionsGetLinesXmlParser extends BaseXmlParser {

	public ActionsGetLinesXmlParser(Context context) {
		super(context);
	}

	public String buildActionGetLinesXML(String personId, String cityCode) throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(dataOutputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, XmlConstants.PAGE);
		serializer.startTag(null, XmlConstants.COMMON);
		serializer.startTag(null, XmlConstants.FUNCTION);

		serializer.attribute(null, XmlConstants.ID, ActionsGetLinesGetter.FUNCTION_NAME);
		serializer.attribute(null, XmlConstants.DEVICE_TYPE, DEVICE_TYPE);
		serializer.attribute(null, XmlConstants.PERSON_ID, personId);
		serializer.attribute(null, XmlConstants.IPHONE_ID, iphone_id);
		serializer.attribute(null, ActionsGetLinesGetter.CITYCODE, cityCode);
		serializer.attribute(null, XmlConstants.VERSION, version);

		serializer.endTag(null, XmlConstants.FUNCTION);
		serializer.endTag(null, XmlConstants.COMMON);
		serializer.endTag(null, XmlConstants.PAGE);
		serializer.endDocument();
		dataOutputStream.close();
		
		return arrayOutputStream.toString();

	}
	public ActionLines parseXml(String writeBack) throws Exception{
		
		byte[] bytes = writeBack.getBytes("UTF-8");
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();

		int status = 0;
		
		ActionLines mActionLines=null;
		List<Line> lines=null;
		Line line=null;
		List<MyPoint> points=null;
		MyPoint point=null;
		
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				mActionLines = new ActionLines();
				break;

			case XmlPullParser.START_TAG:
				if (XmlConstants.RESULT.equals(parser.getName())) {
					status = Integer.valueOf(parser.getAttributeValue(null, XmlConstants.STATUS));
				}
				if (status == 1) {
					if(ActionsGetLinesGetter.CENTER_LX.equals(parser.getName())){
						mActionLines.setCenter_lX(parser.nextText());
					}
					if(ActionsGetLinesGetter.CENTER_LY.equals(parser.getName())){
						mActionLines.setCenter_lY(parser.nextText());
					}
					
					if (ActionsGetLinesGetter.SPAN_LONGIDELTA.equals(parser.getName())) {
						mActionLines.setSpan_longiDelta(parser.nextText());
					}

					if (ActionsGetLinesGetter.SPAN_LANGDELTA.equals(parser.getName())) {
						mActionLines.setSpan_langtDelta(parser.nextText());
					}
					
					if (ActionsGetLinesGetter.ROUTES.equals(parser.getName())) {
						
						lines=new ArrayList<Line>();
					}

					if (ActionsGetLinesGetter.ROUTES_ITEM.equals(parser.getName())) {
						line=new Line();
						points=new ArrayList<MyPoint>();
					}
					if(XmlConstants.ID.equals(parser.getName())){
						line.setId(parser.nextText());
					}
					if(ActionsGetLinesGetter.NAME.equals(parser.getName())){
						line.setName(parser.nextText());
					}
					if(ActionsGetLinesGetter.DIST.equals(parser.getName())){
						line.setDist(parser.nextText());
					}
					if(ActionsGetLinesGetter.NOTICE.equals(parser.getName())){
						line.setNotice(parser.nextText());
					}
					if(ActionsGetLinesGetter.STATUS.equals(parser.getName())){
						line.setStatus(parser.nextText());
					}
					if(ActionsGetLinesGetter.POINTS.equals(parser.getName())){
						point=mActionLines.new MyPoint();
					}
					
					if(ActionsGetLinesGetter.LONGI.equals(parser.getName())){
						point.setLongi((int)(Double.parseDouble(parser.nextText())*1000000));
					}
					if(ActionsGetLinesGetter.LANGT.equals(parser.getName())){
						point.setLangt((int)(Double.parseDouble(parser.nextText())*1000000));
					}

				}
				break;

			case XmlPullParser.END_TAG:
				if (ActionsGetLinesGetter.POINTS.equals(parser.getName())) {
					points.add(point);
				}
				if (ActionsGetLinesGetter.ROUTES_ITEM.equals(parser.getName())) {
					line.setPoints(points);
					lines.add(line);
				}
				if (ActionsGetLinesGetter.ROUTES.equals(parser.getName())) {
					mActionLines.setLines(lines);
				}
				break;

			}
			type = parser.next();
		}
		is.close();
		return mActionLines;
	}
	
}
