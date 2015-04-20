package com.cjj.easy.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cjj.easy.Response;
import com.cjj.easy.VolleyError;
import com.cjj.easy.callback.CallBackDataListener;
import com.cjj.easy.constants.Constants;
import com.cjj.easy.dao.UserDao;
import com.cjj.easy.me.EasyVolley;
import com.cjj.easy.model.Weather;
import com.cjj.easy.model.WeatherInfo;
import com.cjj.easy.utils.VolleyErrorHelper;


public class GsonRequestActivity extends ActionBarActivity implements OnClickListener {
	private Button btn_gson_request;
	private ActionBar actionBar;
	private TextView tv_result ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getString(R.string.GsonRequest));
		setContentView(R.layout.activity_xml_request);
		findView();
	}

	private void findView() 
	{
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		btn_gson_request = (Button) this.findViewById(R.id.btn_xml_request);
		btn_gson_request.setText(getString(R.string.GsonRequest));
		btn_gson_request.setOnClickListener(this);
		tv_result = (TextView) this.findViewById(R.id.tv_XmlRequest);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			this.finish();
			break;
		}
		return true;
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.btn_xml_request:
			solveGsonResult();
			break;
		}
	}

	private void solveGsonResult() {
        /**
         * 这是之前的做法，现在觉得有点累赘，我改了。
         */
//		UserDao.getInstance().getGsonDataFromNet(new CallBackDataListener() {
//
//			@Override
//			public void error(VolleyError error) {
//				tv_result.setText(VolleyErrorHelper.getMessage(error, GsonRequestActivity.this));
//			}
//
//			@Override
//			public void callBack(Object data) {
//				if(data instanceof Weather)
//				{
//					Weather weather = (Weather) data;
//					WeatherInfo weatherInfo = weather.getWeatherinfo();
//					StringBuffer sb = new StringBuffer();
//					sb.append(weatherInfo.getCity()+" "+weatherInfo.getTemp()+"  "+weatherInfo.getTime());
//					tv_result.setText(sb.toString());
//				}
//			}
//		});

        /**
         * 新的方法
         */
        UserDao.getInstance().getGsonDataFromNet(new Response.CallBackListener<Weather>() {
            /**
             * 数据响应成功 返回数据
             * @param data
             */
            @Override
            public void callBack(Weather data) {
                Weather weather = (Weather) data;
					WeatherInfo weatherInfo = weather.getWeatherinfo();
					StringBuffer sb = new StringBuffer();
					sb.append(weatherInfo.getCity()+" "+weatherInfo.getTemp()+"  "+weatherInfo.getTime());
					tv_result.setText(sb.toString());
            }

            /**
             * 数据响应失败 返回数据
             * @param error
             */
            @Override
            public void error(VolleyError error) {
                tv_result.setText(VolleyErrorHelper.getMessage(error, GsonRequestActivity.this));
            }

            /**
             * 打印响应成功的字符串
             * @param result
             */
            @Override
            public void LogString(String result) {
                System.out.print("res = "+result);
                Log.i("cjj","cjj===============>"+result);
            }
        });

	}
	
	@Override
	protected void onStop() {
		EasyVolley.cancelAll(Constants.GSON_TEST);
		super.onStop();
	}
}
