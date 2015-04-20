package com.cjj.easy.model;

import android.app.Application;

import com.cjj.easy.me.EasyVolley;
import com.cjj.easy.utils.LogUtil;

public class VolleyFrameApplication extends Application {

	@Override
	public void onCreate() 
	{
		super.onCreate();
		

		initVolley();
		
	}

	private void initVolley() {
		EasyVolley.init(this);
	}


}
