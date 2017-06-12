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
	static public String cityName = "�Ϸ�";
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
			//ע��㲥
			ViewPagerBoradcast serbro = new ViewPagerBoradcast();
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.example.weather.viewpager");
			registerReceiver(serbro, filter);
			
			weatherInfo.realTime = new ArrayList<HashMap>();//��ʼ��ʵʱ����			
			weatherInfo.time0 = new ArrayList<HashMap>();//��ʼ��δ������
			weatherInfo.time1 = new ArrayList<HashMap>();
			weatherInfo.time2 = new ArrayList<HashMap>();
			weatherInfo.time3 = new ArrayList<HashMap>();
			weatherInfo.time4 = new ArrayList<HashMap>();
			weatherInfo.life = new ArrayList<HashMap>();//��ʼ������ָ��
			weatherInfo.pm2dot5 = new ArrayList<HashMap>();
			mViewPager = (ViewPager) findViewById(R.id.viewpager);
			LayoutInflater inflater = getLayoutInflater();
			final View viewPagerOne = inflater.inflate(R.layout.activity_main, null);
			final View viewPagerTwo = inflater.inflate(R.layout.moreinfo, null);
			ivTianJiaCity = (ImageView) viewPagerOne.findViewById(R.id.iv_tianjia_city);
			//���������Ϣ
			Parameters params = new Parameters();
			params.add("cityname", cityName);
			
			JuheData.executeWithAPI(this,73, "http://op.juhe.cn/onebox/weather/query",
					JuheData.GET, params, new DataCallBack() {						
						@Override
						public void onSuccess(int arg0, String s) {
							try {
								JSONObject result = new JSONObject(s);
								//��ȡʵʱ������Ϣ
								JSONObject weather = result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("weather");
								HashMap<String, String> real = new HashMap<String, String>();
								real.put("temperature", weather.getString("temperature"));
								real.put("week", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getString("week"));
								real.put("cityName", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getString("city_name"));
								                  //��ȡʪ��
								real.put("humidity", weather.getString("humidity"));
								                  //��ȡ����
								real.put("direct", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("wind").getString("direct"));
								                  //��ȡ����
								real.put("power", result.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("wind").getString("power"));
												  //��ȡ��������
								real.put("quality", result.getJSONObject("result").getJSONObject("data").getJSONObject("pm25").getJSONObject("pm25").getString("quality"));
								weatherInfo.realTime.add(real);	
								
								//��ȡ����ָ��
								HashMap<String, String> lifeMap = new HashMap<String, String>();
								JSONArray jsacy = (JSONArray)result.getJSONObject("result").getJSONObject("data").getJSONObject("life").getJSONObject("info").getJSONArray("chuanyi");
								                   //��ȡ����ָ��
								lifeMap.put("chuanyi",jsacy.get(0).toString());
								lifeMap.put("chuanyiMore",jsacy.get(1).toString());
								                   //��ȡ�˶�ָ��
								lifeMap.put("yundong",((JSONArray)result.getJSONObject("result").getJSONObject("data").getJSONObject("life").getJSONObject("info").getJSONArray("yundong")).get(0).toString());
								lifeMap.put("yundongMore",((JSONArray)result.getJSONObject("result").getJSONObject("data").getJSONObject("life").getJSONObject("info").getJSONArray("yundong")).get(1).toString());
								weatherInfo.life.add(lifeMap);
								
								//��ȡPM2.5��Ϣ
								HashMap<String, String> PM25Map = new HashMap<String, String>();
								JSONObject jsapm25 = result.getJSONObject("result").getJSONObject("data").getJSONObject("pm25").getJSONObject("pm25");
								PM25Map.put("PM2.5", jsapm25.getString("pm25"));
								PM25Map.put("PM10", jsapm25.getString("pm10"));
								weatherInfo.pm2dot5.add(PM25Map);
								
								//��ȡ����������Ϣ
								JSONArray mjsonArray0 = result.getJSONObject("result").getJSONObject("data").getJSONArray("weather");
								JSONObject mjs0 = (JSONObject) mjsonArray0.get(0);
								HashMap<String, String> map0 = new HashMap<String, String>();
								                 //�����¶�
								map0.put("temperatureDay", (String) mjs0.getJSONObject("info").getJSONArray("day").get(2));
								                 //��������״��
								map0.put("temperatureDayTianQi", (String) mjs0.getJSONObject("info").getJSONArray("day").get(1));
								                 //ҹ���¶�
								map0.put("temperatureNight", (String) mjs0.getJSONObject("info").getJSONArray("night").get(2));
								                 //ҹ������״��
								map0.put("temperatureNightTianQi", (String) mjs0.getJSONObject("info").getJSONArray("night").get(1));
				                                 //�����ܼ�
							    map0.put("week",  mjs0.getString("week"));
								weatherInfo.time0.add(map0);
								
								//��ȡδ��1��������Ϣ
								JSONArray mjsonArray1 = result.getJSONObject("result").getJSONObject("data").getJSONArray("weather");
								JSONObject mjs1 = (JSONObject) mjsonArray1.get(1);
								HashMap<String, String> map1 = new HashMap<String, String>();
								                 //�����¶�
								map1.put("temperatureDay", (String) mjs1.getJSONObject("info").getJSONArray("day").get(2));
								                 //��������״��
								map1.put("temperatureDayTianQi", (String) mjs1.getJSONObject("info").getJSONArray("day").get(1));
								                 //ҹ���¶�
								map1.put("temperatureNight", (String) mjs1.getJSONObject("info").getJSONArray("night").get(2));
								                 //ҹ������״��
								map1.put("temperatureNightTianQi", (String) mjs1.getJSONObject("info").getJSONArray("night").get(1));
								                 //�����ܼ�
								map1.put("week",  mjs1.getString("week"));
								weatherInfo.time1.add(map1);
								
								//��ȡδ��2��������Ϣ
								JSONObject mjs2 = (JSONObject) mjsonArray1.get(2);
								HashMap<String, String> map2 = new HashMap<String, String>();
								                 //�����¶�
								map2.put("temperatureDay", (String) mjs2.getJSONObject("info").getJSONArray("day").get(2));
								                 //��������״��
								map2.put("temperatureDayTianQi", (String) mjs2.getJSONObject("info").getJSONArray("day").get(1));
								                 //ҹ���¶�
								map2.put("temperatureNight", (String) mjs2.getJSONObject("info").getJSONArray("night").get(2));
								                 //ҹ������״��
								map2.put("temperatureNightTianQi", (String) mjs2.getJSONObject("info").getJSONArray("night").get(1));
								                 //�����ܼ�
								map2.put("week",  mjs2.getString("week"));
								weatherInfo.time2.add(map2);
								
								//��ȡδ��3��������Ϣ
								JSONObject mjs3 = (JSONObject) mjsonArray1.get(3);
								HashMap<String, String> map3 = new HashMap<String, String>();
								                 //�����¶�
								map3.put("temperatureDay", (String) mjs3.getJSONObject("info").getJSONArray("day").get(2));
								                 //��������״��
								map3.put("temperatureDayTianQi", (String) mjs3.getJSONObject("info").getJSONArray("day").get(1));
								                 //ҹ���¶�
								map3.put("temperatureNight", (String) mjs3.getJSONObject("info").getJSONArray("night").get(2));
								                 //ҹ������״��
								map3.put("temperatureNightTianQi", (String) mjs3.getJSONObject("info").getJSONArray("night").get(1));
								                 //�����ܼ�
								map3.put("week",  mjs3.getString("week"));
								weatherInfo.time3.add(map3);
								
								//��ȡδ��4��������Ϣ
								JSONObject mjs4 = (JSONObject) mjsonArray1.get(4);
								HashMap<String, String> map4 = new HashMap<String, String>();
								                 //�����¶�
								map4.put("temperatureDay", (String) mjs4.getJSONObject("info").getJSONArray("day").get(2));
								                 //��������״��
								map4.put("temperatureDayTianQi", (String) mjs4.getJSONObject("info").getJSONArray("day").get(1));
								                 //ҹ���¶�
								map4.put("temperatureNight", (String) mjs4.getJSONObject("info").getJSONArray("night").get(2));
								                 //ҹ������״��
								map4.put("temperatureNightTianQi", (String) mjs4.getJSONObject("info").getJSONArray("night").get(1));
								                 //�����ܼ�
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
							//���¶����¶�
							mainWenDu = (TextView) viewPagerOne.findViewById(R.id.tv_main_wendu);
							mainWenDu.setText((CharSequence) weatherInfo.realTime.get(0).get("temperature")+"��");
							//��������
							tv_main_week = (TextView) viewPagerOne.findViewById(R.id.tv_main_week);
							tv_main_week.setText("����"+((CharSequence) weatherInfo.time0.get(0).get("week")));	
							//���¶���������
							tvCityName = (TextView)viewPagerOne.findViewById(R.id.tv_top_city_name);
							tvCityName.setText(((CharSequence) weatherInfo.realTime.get(0).get("cityName")));
							//���¶�������״��
							tvTianqiTop = (TextView) viewPagerOne.findViewById(R.id.tv_tianqi);
							tvTianqiTop.setText((CharSequence) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
							                      //��������ͼ��
							ivMainTianQiTB = (ImageView) viewPagerOne.findViewById(R.id.iv_main_top_tianqi_tubiao);
							changeTianQiTuBiao(ivMainTianQiTB, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
												  //��������ͼƬ
							llMainBackground = (LinearLayout) viewPagerOne.findViewById(R.id.linelayout_backgroung);
							changeBeiJingTu(llMainBackground, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"),1);
							llMoreBackground = (LinearLayout) viewPagerTwo.findViewById(R.id.linelayout_backgroung_more);
							changeBeiJingTu(llMoreBackground, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"),2);
														
							//���¶����¶ȷ�Χ
							tvWenDuFanWeiTop = (TextView) viewPagerOne.findViewById(R.id.tv_tianqi_wendu_fanwei_top);
							String bottom = (String) weatherInfo.time0.get(0).get("temperatureNight");
							String top = (String) weatherInfo.time0.get(0).get("temperatureDay");
							tvWenDuFanWeiTop.setText(bottom+"��~"+top+"��");
							
							
							//���µײ�δ��1������
							                //����¶�
							tvBottomWenDuNight[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night1);
							tvBottomWenDuNight[0].setText((CharSequence) weatherInfo.time1.get(0).get("temperatureNight")+"��");
				                            //����¶�
							tvBottomWenDuDay[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day1);
							tvBottomWenDuDay[0].setText((CharSequence) weatherInfo.time1.get(0).get("temperatureDay")+"��");
											//����״��
							tvBottomTianQi[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi1);
							tvBottomTianQi[0].setText((CharSequence) weatherInfo.time1.get(0).get("temperatureDayTianQi"));
							ivBottomTianQiTuBiao[0] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao1);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[0], (String) weatherInfo.time1.get(0).get("temperatureDayTianQi"));
							
							                //��������
							tvBottomWeek[0] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week1);
							tvBottomWeek[0].setText("����"+(CharSequence) weatherInfo.time1.get(0).get("week"));
							
							//���µײ�δ��2������
			                                //����¶�
			                tvBottomWenDuNight[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night2);
			                tvBottomWenDuNight[1].setText((CharSequence) weatherInfo.time2.get(0).get("temperatureNight")+"��");
                                            //����¶�
			                tvBottomWenDuDay[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day2);
			                tvBottomWenDuDay[1].setText((CharSequence) weatherInfo.time2.get(0).get("temperatureDay")+"��");
							                //����״��
			                tvBottomTianQi[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi2);
			                tvBottomTianQi[1].setText((CharSequence) weatherInfo.time2.get(0).get("temperatureDayTianQi"));
			                ivBottomTianQiTuBiao[1] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao2);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[1], (String) weatherInfo.time2.get(0).get("temperatureDayTianQi"));
			                
			                                 //��������
			                tvBottomWeek[1] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week2);
			                tvBottomWeek[1].setText("����"+(CharSequence) weatherInfo.time2.get(0).get("week"));
							
			                //���µײ�δ��3������
                                             //����¶�
                            tvBottomWenDuNight[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night3);
                            tvBottomWenDuNight[2].setText((CharSequence) weatherInfo.time3.get(0).get("temperatureNight")+"��");
                                             //����¶�
                            tvBottomWenDuDay[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day3);
                            tvBottomWenDuDay[2].setText((CharSequence) weatherInfo.time3.get(0).get("temperatureDay")+"��");
			                                  //����״��
                            tvBottomTianQi[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi3);
                            tvBottomTianQi[2].setText((CharSequence) weatherInfo.time3.get(0).get("temperatureDayTianQi"));
                            ivBottomTianQiTuBiao[2] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao3);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[2], (String) weatherInfo.time3.get(0).get("temperatureDayTianQi"));
                            
                            
			                                 //��������
                            tvBottomWeek[2] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week3);
			                tvBottomWeek[2].setText("����"+(CharSequence) weatherInfo.time3.get(0).get("week"));
			                
			                //���µײ�δ��4������
                                              //����¶�
                            tvBottomWenDuNight[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_night4);
                            tvBottomWenDuNight[3].setText((CharSequence) weatherInfo.time4.get(0).get("temperatureNight")+"��");
                                              //����¶�
                            tvBottomWenDuDay[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_wendu_day4);
                            tvBottomWenDuDay[3].setText((CharSequence) weatherInfo.time4.get(0).get("temperatureDay")+"��");
                                               //����״��
                            tvBottomTianQi[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_tianqi4);
                            tvBottomTianQi[3].setText((CharSequence) weatherInfo.time4.get(0).get("temperatureDayTianQi"));
                            ivBottomTianQiTuBiao[3] = (ImageView) viewPagerOne.findViewById(R.id.iv_main_bottom_tianqi_tubiao4);
							changeTianQiTuBiao(ivBottomTianQiTuBiao[3], (String) weatherInfo.time4.get(0).get("temperatureDayTianQi"));
                            
                                              //��������
                            tvBottomWeek[3] = (TextView) viewPagerOne.findViewById(R.id.tv_bottom_week4);
                            tvBottomWeek[3].setText("����"+(CharSequence) weatherInfo.time4.get(0).get("week"));
			                
                            
                            //������ϸ��Ϣ����
                                                   //����ʪ��
                            tvMoreShiDu = (TextView) viewPagerTwo.findViewById(R.id.tv_more_info_shidu);
                            tvMoreShiDu.setText((CharSequence) weatherInfo.realTime.get(0).get("humidity")+"%");
                                                   //���¶�������״��
                            tvMoreTianTop = (TextView) viewPagerTwo.findViewById(R.id.tv_more_tianqi_top);
                            tvMoreTianTop.setText((CharSequence) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
							ivMoreTianQiTuBiao = (ImageView) viewPagerTwo.findViewById(R.id.iv_more_tianqi_tubiao);
							changeTianQiTuBiao(ivMoreTianQiTuBiao, (String) weatherInfo.time0.get(0).get("temperatureDayTianQi"));
                            
                            //��������¶�
                            tvMoreTemparture = (TextView) viewPagerTwo.findViewById(R.id.tv_more_temparture);
                            tvMoreTemparture.setText((CharSequence) weatherInfo.realTime.get(0).get("temperature")+"��");
                            					   //���·���ͷ���
                            tvMoreWindForce = (TextView) viewPagerTwo.findViewById(R.id.tv_more_wind_force);
                            tvMoreWindForce.setText((CharSequence) weatherInfo.realTime.get(0).get("direct")+"/"+weatherInfo.realTime.get(0).get("power"));
                            					   //���¿�������
                            tvMoreQuality = (TextView) viewPagerTwo.findViewById(R.id.tv_more_info_quality);
                            tvMoreQuality.setText((CharSequence) weatherInfo.realTime.get(0).get("quality"));
                            					   //���´���ָ��
                            tvMoreChuanYi = (TextView) viewPagerTwo.findViewById(R.id.tv_more_chuanyi);
                            tvMoreChuanYiMore = (TextView) viewPagerTwo.findViewById(R.id.tv_more_chuanyimore); 
                            tvMoreChuanYi.setText((CharSequence) weatherInfo.life.get(0).get("chuanyi"));
                            tvMoreChuanYiMore.setText((CharSequence) weatherInfo.life.get(0).get("chuanyiMore"));
                                                   //���´���ָ��
                            tvMoreYunDong = (TextView) viewPagerTwo.findViewById(R.id.tv_more_yundong);
                            tvMoreYunDongMore = (TextView) viewPagerTwo.findViewById(R.id.tv_more_yundongmore);
                            tvMoreYunDong.setText((CharSequence) weatherInfo.life.get(0).get("yundong"));
                            tvMoreYunDongMore.setText((CharSequence) weatherInfo.life.get(0).get("yundongMore"));
                            					   //����PM2.5&PM10
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
								
			//��ӳ���
			ivTianJiaCity.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MyViewPager.this, AddCity.class);
					MyViewPager.this.startActivity(intent);
				}
			});						
		}
				
		//��������ͼ��
		public void changeTianQiTuBiao(ImageView imageView,String tianQiInfo){
			if("����".equals(tianQiInfo)||"�е�����".equals(tianQiInfo)){
			    imageView.setImageResource(R.drawable.l_rain_big);			
			}else if("��".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_sun);
			}else if("����".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_cloudy);
			}else if("����".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_shower);
			}else if("������".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_hail);
			}else if("С��".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_small);
			}else if("����".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_medium);
			}else if("����".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_storm);
			}else if("����".equals(tianQiInfo)){
				imageView.setImageResource(R.drawable.l_rain_bigstorm);
			}			
		}
		
		//���ı���ͼƬ
		public void changeBeiJingTu(LinearLayout linearLayout,String tianQiInfo,int flag){			
			if(flag == 1){//���������汳��
				if("�е�����".equals(tianQiInfo)||"����".equals(tianQiInfo)||"С��".equals(tianQiInfo)||"����".equals(tianQiInfo)||"����".equals(tianQiInfo)||"����".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.rain_day);
				}else if("��".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.sun_day);
				}else if("����".equals(tianQiInfo)||"������".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.thunderrain_day);
				}else if("����".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.cloudy_day);
				}
				
			}else if(flag == 2){//����More����
				if("�е�����".equals(tianQiInfo)||"����".equals(tianQiInfo)||"С��".equals(tianQiInfo)||"����".equals(tianQiInfo)||"����".equals(tianQiInfo)||"����".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_rain_day);
				}else if("��".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_sun_day);
				}else if("����".equals(tianQiInfo)||"������".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_thunderrain_day);
				}else if("����".equals(tianQiInfo)){
					linearLayout.setBackgroundResource(R.drawable.blur_cloudy_day);
				}	
			}			
		}
		//�½��㲥
		public class ViewPagerBoradcast extends BroadcastReceiver{

			@Override
			public void onReceive(Context arg0, Intent intent) {
				cityName = intent.getStringExtra("cityName");
				System.out.println(cityName);
			}
			
		}
		
		
}
