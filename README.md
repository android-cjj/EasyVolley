EasyVolley
===========================================================

说明
---------------------------------------------------------
    volley的好处，呵呵，自己百度谷歌。这里想讲的是volley的使用及怎样更简单的使用volley,当然简单是相对于个人来说的，我也只是新手，只是学会了怎么用和封装了一些东西让它更容易 ，更有效的使用。。。。。。。
例子
--------------------------------------------------------------
    这里我写了 "StringRequest的用法" , "JsonRequest的用法" , "imageRequest的用法" , "ImagLoaderRequest的用法" ,"NetworkImageViewRequest的用法" , "XMLRequest的用,"GsonRequest的用法"，其中“GsonRequest”有做了封装，其他都是官方的使用方法。
使用
--------------------------------------------------------------------
   这里就拿“GsonRequest”的用法说说把。
###(1)在Application中初始化Volley
   public class VolleyFrameApplication extends Application {
	@Override
	public void onCreate() 
	{
		super.onCreate();

        /**
         * 初始化Volley
         */
		initVolley();
	}

	private void initVolley() {
		EasyVolley.init(this);
	}
}
###(2)写好model类，不知道json字符串 LogString(String result) 打印，然后获取数据
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
					sb.append(weatherInfo.getCity()+" "+weatherInfo.getTemp()+" "+weatherInfo.getTime());
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
                Log.i("cjj","cjj===============>"+result);
            }
        });
		
###（3）业务逻辑类可以这样写的
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
	
    public void getGsonDataFromNet(Response.CallBackListener<Weather> callBackListener)
    {
        GsonRequest<Weather> mRequest = new GsonRequest<Weather>(Constants.GSON_TEST,Weather.class,callBackListener);
        EasyVolley.addRequest(mRequest, Constants.TAG_REQUEST_GSON);
    }

}

具体用法你可以看代码，很简单的，呵呵。。。。。。

效果图：

![](http://www.apkbus.com/data/attachment/forum/201504/20/183541n8738038we00vaex.gif)  




