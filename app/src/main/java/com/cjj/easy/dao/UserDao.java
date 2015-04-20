package com.cjj.easy.dao;

import com.cjj.easy.Response;
import com.cjj.easy.VolleyError;
import com.cjj.easy.callback.CallBackDataListener;
import com.cjj.easy.constants.Constants;
import com.cjj.easy.custom.request.GsonRequest;
import com.cjj.easy.custom.request.XMLRequest;
import com.cjj.easy.me.EasyVolley;
import com.cjj.easy.model.Weather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class UserDao {
	/** 私有类对象 */
	private static UserDao instance;

	/** 单例模式 */
	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}

		return instance;
	}

	/**
	 * xml request 请求
	 * 
	 * @param callBackDataListener
	 */
	public void getXmlResultFormNet(
			final CallBackDataListener callBackDataListener) {
		XMLRequest xmlRequest = new XMLRequest(Constants.XML_TEST,
				new Response.Listener<XmlPullParser>() {
					@Override
					public void onResponse(XmlPullParser response) {
						callBackDataListener.callBack(parseXmlData(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						callBackDataListener.error(error);
					}
				});

		EasyVolley.addRequest(xmlRequest, Constants.TAG_REQUEST_XML);
	}

	/**
	 * 解析xml
	 * 
	 * @param response
	 */
	public String parseXmlData(XmlPullParser response) {
		StringBuffer sb = new StringBuffer();
		try {
			int evenType = response.getEventType();
			while (evenType != XmlPullParser.END_DOCUMENT) {
				switch (evenType) {
				case XmlPullParser.START_TAG:

					String nodeName = response.getName();
					if ("city".equals(nodeName)) {
						String pName = response.getAttributeValue(0);
						sb.append(pName + "\n");
					}
					break;

				}

				evenType = response.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

//	/**
//	 * get gson data
//	 *
//	 * @param callBackDataListener
//	 */
//	public void getGsonDataFromNet(final CallBackDataListener callBackDataListener) {
//		GsonRequest<Weather> mRequest = new GsonRequest<Weather>(
//				Constants.GSON_TEST, Weather.class,
//				new Response.Listener<Weather>() {
//					@Override
//					public void onResponse(Weather response) {
//						callBackDataListener.callBack(response);
//					}
//				}, new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						callBackDataListener.error(error);
//					}
//				});
//
//        EasyVolley.addRequest(mRequest, Constants.TAG_REQUEST_GSON);
//	}

    public void getGsonDataFromNet(Response.CallBackListener<Weather> callBackListener)
    {
        GsonRequest<Weather> mRequest = new GsonRequest<Weather>(Constants.GSON_TEST,Weather.class,callBackListener);
        EasyVolley.addRequest(mRequest, Constants.TAG_REQUEST_GSON);
    }



}
