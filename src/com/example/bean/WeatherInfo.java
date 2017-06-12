package com.example.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherInfo {

	/*
	 * String cityName;//城市名
	 * String week;//星期
	 * String data;//日期
	 * String time;//时间
	 * String temperature;//温度
	 * String humidity;//湿度
	 * String info;//雾等
	 * String direct;// 风向
	 * String power;// 风力
	 * */
	public ArrayList<HashMap> realTime;//当前天气
	
	/*
	 * String chuanyi;//穿衣
	 * String yundong;//运动
	 * String data;//日期	
	 * */
	public ArrayList<HashMap> life;//生活指数
	

	/*
	 * String cityName;//城市名
	 * String week;//星期
	 * String data;//日期
	 * String time;//时间
	 * String day;//白天
	 * String night;//晚上	
	 * */
	public ArrayList<HashMap> time0;//当天天气
	public ArrayList<HashMap> time1;//未来1天
	public ArrayList<HashMap> time2;//未来2天
	public ArrayList<HashMap> time3;//未来3天
	public ArrayList<HashMap> time4;//未来4天
	public ArrayList<HashMap> time5;//未来5天
	public ArrayList<HashMap> time6;//未来6天
	
	
	/*
	 * String data;//日期
	 * String pm2_5;//PM2.5
	 * String pm10;//PM10
	 * String quality//空气质量
	 * */
	public ArrayList<HashMap> pm2dot5;//PM2.5
	
	
}
