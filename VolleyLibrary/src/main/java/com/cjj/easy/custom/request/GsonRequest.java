package com.cjj.easy.custom.request;


import com.cjj.easy.AuthFailureError;
import com.cjj.easy.NetworkResponse;
import com.cjj.easy.ParseError;
import com.cjj.easy.Request;
import com.cjj.easy.Response;
import com.cjj.easy.Response.ErrorListener;
import com.cjj.easy.Response.Listener;
import com.cjj.easy.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 
 * 自定义GsonRequest
 * @author cjj
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {
	private final Gson mGson = new Gson();
	private final Class<T> mClazz;
	private final Map<String, String> mHeaders;
	private Map<String, String> mMap;
    private Response.CallBackListener<T> callBackListener;


    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                        Response.CallBackListener<T> callBackListener)
    {
        super(method, url, null,callBackListener);
        this.mClazz = clazz;
        this.mHeaders = headers;
        this.callBackListener = callBackListener;
    }


    public GsonRequest(String url, Class<T> clazz,  Response.CallBackListener<T> callBackListener)
	{
		this(Method.GET, url, clazz,  null, callBackListener);
	}

    public GsonRequest(String url, Class<T> clazz,Response.CallBackListener<T> callBackListener, Map<String, String> mMap)
    {
        this(Method.POST, url, clazz,  null, callBackListener);
        this.mMap = mMap;
    }


	
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mMap;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError
	{
		return mHeaders != null ? mHeaders : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response)
	{
		callBackListener.callBack(response);

	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response)
	{
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            if(callBackListener!=null)
            {
                callBackListener.LogString(json);
            }
			return Response.success(mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}
