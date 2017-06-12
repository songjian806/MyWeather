package com.example.myweather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.ViewPagerAdapter;
import com.example.bean.WeatherInfo;
import com.example.myweather.AddCity.AddCityBoradcast;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyViewPager extends Activity{
	ViewPager mViewPager;
	List<View> mList;
	ImageView ivTianJiaCity;
	static WeatherInfo weatherInfo= new WeatherInfo();
	static public String cityName = "合肥";
	TextView mainWenDu;
	TextView tv_main_week;
	TextView tvCityName;
	TextView tvTianqiTop;
	TextView tvWenDuFanWeiTop;
	TextView[] tvBottomWenDuNight = new TextView[4];
	TextView[] tvBottomWenDuDay = new TextView[4];
	TextView[] tvBottomTianQi = new TextView[4];
	TextView[] tvBottomWeek = new TextView[4];
	TextView tvMoreShiDu;
	TextView tvMorequality;
	TextView tvMoreTianTop;
	TextView tvMoreTemparture;
	TextView tvMoreWindForce;
	TextView tvMoreQuality;
	TextView tvMoreChuanYi;
	TextView tvMoreChuanYiMore;
	TextView tvMoreYunDong;
	TextView tvMoreYunDongMore;
	TextView tvMorePM25;
	TextView tvMorePM10;
	ImageView ivMainTianQiTB;
	LinearLayout llMainBackground;
	LinearLayout llMoreBackground;
	RelativeLayout rlViewPagerTopBackg;
	ImageView[] ivBottomTianQiTuBiao = new ImageView[4];
	ImageView ivMoreTianQiTuBiao;
		@SuppressWarnings("rawtypes")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.myviewpager);
			mList = new ArrayList<View>();			
			//注册广播
			ViewPagerBoradcast serbro = new ViewPagerBoradcast();
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.example.weather.viewpager");
			registerReceiver(serbro, filter);
			
			weatherInfo.realTime = new ArrayList<HashMap>();//初始化实时天气			
			weatherInfo.time0 = new ArrayList<HashMap>();//初始化未来天气
			weatherInfo.time1 = new ArrayList<HashMap>();
			weatherInfo.time2 = new ArrayList<HashMap>();
			weatherInfo.time3 = new ArrayList<HashMap>();
			weatherInfo.time4 = new ArrayList<HashMap>();
			weatherInfo.life = new ArrayList<HashMap>();//初始化生活指数
			weatherInfo.pm2dot5 = new ArrayList<HashMap>();
			mViewPager = (ViewPager) findViewById(R.id.viewpager);
			LayoutInflater inflater = getLayoutInflater();
			final View viewPagerOne = inflater.inflate(R.layout.activity_main, null);
			final View viewPagerTwo = inflater.inflate(R.layout.moreinfo, null);
			ivTianJiaCity = (ImageView) viewPagerOne.findViewById(R.id.iv_tianjia_city);
			//获得天气信息
			Parameters params = new Parameters();
			params.add("cityname", cityName);
			
			JuheData.executeWithAPI(this,73, "http://op.juhe.cn/onebox/weather/query",
					JuheData.GET, params, new DataCallBack() {						
						@Override
						public void onSuccess(int arg0, String s) {
							try {
								JSONObject result = new JSONObject(s);
								//获取实时天气信息
								JSONObject weather = result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("weather");
								HashMap<String, String> real = new HashMap<String, String>();
								real.put("temperature", weather.getString("temperature"));
								real.put("week", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getString("week"));
								real.put("cityName", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getString("city_name"));
								                  //获取湿度
								real.put("humidity", weather.getString("humidity"));
								                  //获取风向
								real.put("direct", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("wind").getString("direct"));
								                  //获取风力
								real.put("power", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("wind").getString("power"));
												  //获取空气质量
								real.put("quality", result.getJSONObject("result").getJSONObject("data").getJSONObject("pm25").getJSONObject("pm25").getString("quality"));
								weatherInfo.realTime.add(real);	
								
								//获取生活指数
								HashMap<String, String> lifeMap = new HashMap<String, String>();
								JSONArray jsacy = (JSONArray)result.getJSONObject("result").getJSONObject("data").getJSONObject("life").getJSONObject("info").getJSONArray("chuanyi");
								                   //获取穿衣指数
								lifeMap.put("chuanyi",jsacy.get(0).toString());
								lifeMap.put("chuanyiMore",jsacy.get(1).toString());
								                   //获取运动指数
								lifeMap.put("yundong",((JSONArray)result.getJSONObject("result").getJSONObject("data").getJSONObject("life").getJSONObject("info").getJSONArray("yundong")).get(0).toString());
								lifeMap.put("yundongMore",((JSONArray)result.getJSONObject("result").getJSONObject("data").getJSONObject("life").getJSONObject("info").getJSONArray("yundong")).get(1).toString());
								weatherInfo.life.add(lifeMap);
								
								//获取PM2.5信息
								HashMap<String, String> PM25Map = new HashMap<String, String>();
								JSONObject jsapm25 = result.getJSONObject("result").getJSONObject("data").getJSONObject("pm25").getJSONObject("pm25");
								PM25Map.put("PM2.5", jsapm25.getString("pm25"));
								PM25Map.put("PM10", jsapm25.getString("pm10"));
								weatherInfo.pm2dot5.add(PM25Map);
								
								//获取当天天气信息
								JSONArray mjsonArray0 = result.getJSONObject("result").getJSONObject("data").getJSONArray("weather");
								JSONObject mjs0 = (JSONObject) mjsonArray0.get(0);
								HashMap<String, String> map0 = new HashMap<String, String>();
								                 //白天温度
								map0.put("temperatureDay", (String) mjs0.getJSONObject("info").getJSONArray("day").get(2));
								                 //白天天气状况
								map0.put("temperatureDayTianQi", (String) mjs0.getJSONObject("info").getJSONArray("day").get(1));
								                 //夜晚温度
								map0.put("temperatureNight", (String) mjs0.getJSONObject("info").getJSONArray("night").get(2));
								                 //夜晚天气状况
								map0.put("temperatureNightTianQi", (String) mjs0.getJSONObject("info").getJSONArray("night").get(1));
				                                 //此日周几
							    map0.put("week",  mjs0.getString("week"));
								weatherInfo.time0.add(map0);
								
								//获取未来1天天气信息
								JSONArray mjsonArray1 = result.getJSONObject("result").getJSONObject("data").getJSONArray("weather");
								JSONObject mjs1 = (JSONObject) mjsonArray1.get(1);
								HashMap<String, String> map1 = new HashMap<String, String>();
								                 //白天温度
								map1.put("temperatureDay", (String) mjs1.getJSONObject("info").getJSONArray("day").get(2));
								                 //白天天气状况
								map1.put("temperatureDayTianQi", (String) mjs1.getJSONObject("info").getJSONArray("day").get(1));
								                 //夜晚温度
								map1.put("temperatureNight", (String) mjs1.getJSONObject("info").getJSONArray("night").get(2));
								                 //夜晚天气状况
								map1.put("temperatureNightTianQi", (String) mjs1.getJSONObject("info").getJSONArray("night").get(1));
								                 //此日周几
								map1.put("week",  mjs1.getString("week"));
								weatherInfo.time1.add(map1);
								
								//获取未来2天天气信息
								JSONObject mjs2 = (JSONObject) mjsonArray1.get(2);
								HashMap<String, String> map2 = new HashMap<String, String>();
								                 //白天温度
								map2.put("temperatureDay", (String) mjs2.getJSONObject("info").getJSONArray("day").get(2));
								                 //白天天气状况
								map2.put("temperatureDayTianQi", (String) mjs2.getJSONObject("info").getJSONArray("day").get(1));
								                 //夜晚温度
								map2.put("temperatureNight", (String) mjs2.getJSONObject("info").getJSONArray("night").get(2));
								                 //夜晚天气状况
								map2.put("temperatureNightTianQi", (String) mjs2.getJSONObject("info").getJSONArray("night").get(1));
								                 //此日周几
								map2.put("week",  mjs2.getString("week"));
								weatherInfo.time2.add(map2);
								
								//获取未来3天天气信息
								JSONObject mjs3 = (JSONObject) mjsonArray1.get(3);
								HashMap<String, String> map3 = new HashMap<String, String>();
								                 //白天温度
								map3.put("temperatureDay", (String) mjs3.getJSONObject("info").getJSONArray("day").get(2));
								                 //白天天气状况
								map3.put("temperatureDayTianQi", (String) mjs3.getJSONObject("info").getJSONArray("day").get(1));
								                 //夜晚温度
								map3.put("temperatureNight", (String) mjs3.getJSONObject("info").getJSONArray("night").get(2));
								                 //夜晚天气状况
								map3.put("temperatureNightTianQi", (String) mjs3.getJSONObject("info").getJSONArray("night").get(1));
								                 //此日周几
								map3.put("week",  mjs3.getString("week"));
								weatherInfo.time3.add(map3);
								
								//获取未来4天天气信息
								JSONObject mjs4 = (JSONObject) mjsonArray1.get(4);
								HashMap<String, String> map4 = new HashMap<String, String>();
								                 //白天温度
								map4.put("temperatureDay", (String) mjs4.getJSONObject("info").getJSONArray("day").get(2));
								                 //白天天气状况
								map4.put("temperatureDayTianQi", (String) mjs4.getJSONObject("info").getJSONArray("day").get(1));
								                 //夜晚温度
								map4.put("temperatureNight", (String) mjs4.getJSONObject("info").getJSONArray("night").get(2));
								                 //夜晚天气状况
								map4.put("temperatureNightTianQi", (String) mjs4.getJSONObject("info").getJSONArray("night").get(1));
								                 //此日周几
								map4.put("week",  mjs4.getString("week"));
								weatherInfo.time4.add(map4);
								
								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}						
						}
						
						@SuppressLint("CutPasteId")
						@Override
						public void onFinish(){
							//更新顶部温度
							mainWenDu = (TextView) viewPagerOne.findViewById(R.id.tv_main_wendu);
							mainWenDu.setText((CharSequence) weatherInfo.realTime.get(0).get("temperature")+"°");
							//更新星期
							tv_main_week = (TextView) viewPagerOne.findViewById(R.id.tv_main_week);
							tv_main_week.setText("星期"+((CharSequence) weatherInfo.time0.get(0).get("week")));	
							//更新顶部城市名
							tvCityName = (TextView)viewPagerOne.findViewById(R.id.tv_top_city_name);
							tvCityName.setText(((CharSequence) weatherInfo.realTime.get(0).get("cityName")));
							//更新顶部天气状况
							tvTianqiTop = (TextView) viewPagerOne.findViewById(R.id.tv_tianqi);
							tvTianqiTop.setText((CharSequence) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
							                      //更换天气图标
							ivMainTianQiTB = (ImageView) viewPagerOne.findViewById(R.id.iv_main_top_tianqi_tubiao);
							changeTianQiTuBiao(ivMainTianQiTB, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
												  //更换背景图片
							llMainBackground = (LinearLayout) viewPagerOne.findViewById(R.id.linelayout_backgroung);
							changeBeiJingTu(llMainBackground, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"),1);
							llMoreBackground = (LinearLayout) viewPagerTwo.findViewById(R.id.linelayout_backgroung_more);
							changeBeiJingTu(llMoreBackground, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"),2);
														
							//更新顶部温度范围
							tvWenDuFanWeiTop = (TextView) viewPagerOne.findViewById(R.id.tv_tianqi_wendu_fanwei_top);
							String bottom = (String) weatherInfo.time0.get(0).get("temperatureNight");
							String top = (String) weatherInfo.time0.get(0).get("temperatureDay");
							tvWenDuFanWeiTop.setText(bottom+"°~"+top+"°");
							
							
							//更新底部未来1天天气
							                //最低温度
							tvBottomWenDuNight[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night1);
							tvBottomWenDuNight[0].setText((CharSequence) weatherInfo.time1.get(0).get("temperatureNight")+"°");
				                            //最高温度
							tvBottomWenDuDay[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day1);
							tvBottomWenDuDay[0].setText((CharSequence) weatherInfo.time1.get(0).get("temperatureDay")+"°");
											//天气状况
							tvBottomTianQi[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi1);
							tvBottomTianQi[0].setText((CharSequence) weatherInfo.time1.get(0).get("temperatureDayTianQi"));
							ivBottomTianQiTuBiao[0] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao1);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[0], (String) weatherInfo.time1.get(0).get("temperatureDayTianQi"));
							
							                //更新星期
							tvBottomWeek[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week1);
							tvBottomWeek[0].setText("星期"+(CharSequence) weatherInfo.time1.get(0).get("week"));
							
							//更新底部未来2天天气
			                                //最低温度
			                tvBottomWenDuNight[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night2);
			                tvBottomWenDuNight[1].setText((CharSequence) weatherInfo.time2.get(0).get("temperatureNight")+"°");
                                            //最高温度
			                tvBottomWenDuDay[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day2);
			                tvBottomWenDuDay[1].setText((CharSequence) weatherInfo.time2.get(0).get("temperatureDay")+"°");
							                //天气状况
			                tvBottomTianQi[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi2);
			                tvBottomTianQi[1].setText((CharSequence) weatherInfo.time2.get(0).get("temperatureDayTianQi"));
			                ivBottomTianQiTuBiao[1] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao2);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[1], (String) weatherInfo.time2.get(0).get("temperatureDayTianQi"));
			                
			                                 //更新星期
			                tvBottomWeek[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week2);
			                tvBottomWeek[1].setText("星期"+(CharSequence) weatherInfo.time2.get(0).get("week"));
							
			                //更新底部未来3天天气
                                             //最低温度
                            tvBottomWenDuNight[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night3);
                            tvBottomWenDuNight[2].setText((CharSequence) weatherInfo.time3.get(0).get("temperatureNight")+"°");
                                             //最高温度
                            tvBottomWenDuDay[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day3);
                            tvBottomWenDuDay[2].setText((CharSequence) weatherInfo.time3.get(0).get("temperatureDay")+"°");
			                                  //天气状况
                            tvBottomTianQi[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi3);
                            tvBottomTianQi[2].setText((CharSequence) weatherInfo.time3.get(0).get("temperatureDayTianQi"));
                            ivBottomTianQiTuBiao[2] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao3);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[2], (String) weatherInfo.time3.get(0).get("temperatureDayTianQi"));
                            
                            
			                                 //更新星期
                            tvBottomWeek[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week3);
			                tvBottomWeek[2].setText("星期"+(CharSequence) weatherInfo.time3.get(0).get("week"));
			                
			                //更新底部未来4天天气
                                              //最低温度
                            tvBottomWenDuNight[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night4);
                            tvBottomWenDuNight[3].setText((CharSequence) weatherInfo.time4.get(0).get("temperatureNight")+"°");
                                              //最高温度
                            tvBottomWenDuDay[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day4);
                            tvBottomWenDuDay[3].setText((CharSequence) weatherInfo.time4.get(0).get("temperatureDay")+"°");
                                               //天气状况
                            tvBottomTianQi[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi4);
                            tvBottomTianQi[3].setText((CharSequence) weatherInfo.time4.get(0).get("temperatureDayTianQi"));
                            ivBottomTianQiTuBiao[3] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao4);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[3], (String) weatherInfo.time4.get(0).get("temperatureDayTianQi"));
                            
                                              //更新星期
                            tvBottomWeek[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week4);
                            tvBottomWeek[3].setText("星期"+(CharSequence) weatherInfo.time4.get(0).get("week"));
			                
                            
                            //更新详细信息界面
                                                   //更新湿度
                            tvMoreShiDu = (TextView) viewPagerTwo.findViewById(R.id.tv_more_info_shidu);
                            tvMoreShiDu.setText((CharSequence) weatherInfo.realTime.get(0).get("humidity")+"%");
                                                   //更新顶部天气状况
                            tvMoreTianTop = (TextView) viewPagerTwo.findViewById(R.id.tv_more_tianqi_top);
                            tvMoreTianTop.setText((CharSequence) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
							ivMoreTianQiTuBiao = (ImageView) viewPagerTwo.findViewById(R.id.iv_more_tianqi_tubiao);
							changeTianQiTuBiao(ivMoreTianQiTuBiao, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
                            
                            //更新体感温度
                            tvMoreTemparture = (TextView) viewPagerTwo.findViewById(R.id.tv_more_temparture);
                            tvMoreTemparture.setText((CharSequence) weatherInfo.realTime.get(0).get("temperature")+"°");
                            					   //更新风向和风力
                            tvMoreWindForce = (TextView) viewPagerTwo.findViewById(R.id.tv_more_wind_force);
                            tvMoreWindForce.setText((CharSequence) weatherInfo.realTime.get(0).get("direct")+"/"+weatherInfo.realTime.get(0).get("power"));
                            					   //更新空气质量
                            tvMoreQuality = (TextView) viewPagerTwo.findViewById(R.id.tv_more_info_quality);
                            tvMoreQuality.setText((CharSequence) weatherInfo.realTime.get(0).get("quality"));
                            					   //更新穿衣指数
                            tvMoreChuanYi = (TextView) viewPagerTwo.findViewById(R.id.tv_more_chuanyi);
                            tvMoreChuanYiMore = (TextView) viewPagerTwo.findViewById(R.id.tv_more_chuanyimore); 
                            tvMoreChuanYi.setText((CharSequence) weatherInfo.life.get(0).get("chuanyi"));
                            tvMoreChuanYiMore.setText((CharSequence) weatherInfo.life.get(0).get("chuanyiMore"));
                                                   //更新穿衣指数
                            tvMoreYunDong = (TextView) viewPagerTwo.findViewById(R.id.tv_more_yundong);
                            tvMoreYunDongMore = (TextView) viewPagerTwo.findViewById(R.id.tv_more_yundongmore);
                            tvMoreYunDong.setText((CharSequence) weatherInfo.life.get(0).get("yundong"));
                            tvMoreYunDongMore.setText((CharSequence) weatherInfo.life.get(0).get("yundongMore"));
                            					   //更新PM2.5&PM10
                            tvMorePM25 = (TextView) viewPagerTwo.findViewById(R.id.tv_more_pm25);
                            tvMorePM25.setText((CharSequence) weatherInfo.pm2dot5.get(0).get("PM2.5"));
                            tvMorePM10 = (TextView) viewPagerTwo.findViewById(R.id.tv_more_pm10);
                            tvMorePM10.setText((CharSequence) weatherInfo.pm2dot5.get(0).get("PM10"));
                            
                            
							mList.add(viewPagerOne);
							mList.add(viewPagerTwo);
							ViewPagerAdapter mAdapter = new ViewPagerAdapter(mList);
							mViewPager.setAdapter(mAdapter);	
						}
						
						@Override
						public void onFailure(int arg0, String arg1, Throwable arg2) {
							// TODO Auto-generated method stub
							
						}
					});
								
			//添加城市
			ivTianJiaCity.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MyViewPager.this, AddCity.class);
					MyViewPager.this.startActivity(intent);
				}
			});						
		}
				
		//更换天气图标
		public void changeTianQiTuBiao(ImageView imageView,String tianQiInfo){
			if("大雨".equals(tianQiInfo)||"中到大雨".equals(tianQiInfo)){
			    imageView.setImageResource(R.drawable.l_rain_big);			
			}else if("晴".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_sun);
			}else if("多云".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_cloudy);
			}else if("阵雨".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_shower);
			}else if("雷阵雨".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_hail);
			}else if("小雨".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_small);
			}else if("中雨".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_medium);
			}else if("暴雨".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_storm);
			}else if("大暴雨".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_bigstorm);
			}			
		}
		
		//更改背景图片
		public void changeBeiJingTu(LinearLayout linearLayout,String tianQiInfo,int flag){			
			if(flag == 1){//更新主界面背景
				if("中到大雨".equals(tianQiInfo)||"大雨".equals(tianQiInfo)||"小雨".equals(tianQiInfo)||"中雨".equals(tianQiInfo)||"暴雨".equals(tianQiInfo)||"大暴雨".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.rain_day);
				}else if("晴".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.sun_day);
				}else if("阵雨".equals(tianQiInfo)||"雷阵雨".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.thunderrain_day);
				}else if("多云".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.cloudy_day);
				}
				
			}else if(flag == 2){//更新More背景
				if("中到大雨".equals(tianQiInfo)||"大雨".equals(tianQiInfo)||"小雨".equals(tianQiInfo)||"中雨".equals(tianQiInfo)||"暴雨".equals(tianQiInfo)||"大暴雨".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_rain_day);
				}else if("晴".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_sun_day);
				}else if("阵雨".equals(tianQiInfo)||"雷阵雨".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_thunderrain_day);
				}else if("多云".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_cloudy_day);
				}	
			}			
		}
		//新建广播
		public class ViewPagerBoradcast extends BroadcastReceiver{

			@Override
			public void onReceive(Context arg0, Intent intent) {
				cityName = intent.getStringExtra("cityName");
				System.out.println(cityName);
			}
			
		}
		
		
}
