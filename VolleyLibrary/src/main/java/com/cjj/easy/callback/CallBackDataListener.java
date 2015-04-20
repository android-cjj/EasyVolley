package com.cjj.easy.callback;

import com.cjj.easy.VolleyError;

/**
 * 数据回调接口
 * @author cjj
 *
 */
public interface CallBackDataListener {
	
	public void callBack(Object data);
	
	public void error(VolleyError error);
}
