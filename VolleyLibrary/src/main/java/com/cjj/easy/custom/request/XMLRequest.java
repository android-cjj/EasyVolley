package com.cjj.easy.custom.request;

import com.cjj.easy.NetworkResponse;
import com.cjj.easy.ParseError;
import com.cjj.easy.Request;
import com.cjj.easy.Response;
import com.cjj.easy.Response.ErrorListener;
import com.cjj.easy.Response.Listener;
import com.cjj.easy.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * 自定义XMLRequest
 * @author cjj
 *
 */
public class XMLRequest extends Request<XmlPullParser> {

	private final Listener<XmlPullParser> mListener;

	public XMLRequest(int method, String url, Listener<XmlPullParser> listener,
			ErrorListener errorListener)
	{
		super(method, url, errorListener);
		mListener = listener;
	}

	public XMLRequest(String url, Listener<XmlPullParser> listener,
			ErrorListener errorListener)
	{
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected Response<XmlPullParser> parseNetworkResponse(
			NetworkResponse response)
	{
		try {
			String xmlString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlString));
			return Response.success(xmlPullParser,
                    HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (XmlPullParserException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(XmlPullParser response)
	{
		mListener.onResponse(response);
	}

}