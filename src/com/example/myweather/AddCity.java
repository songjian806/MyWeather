package com.example.myweather;

import java.util.ArrayList;

import com.example.adapter.CityAdapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddCity extends Activity {
	GridView mGridView;
	ArrayList<String> cityList;
	TextView tvCancle;
	ImageView ivAddCitySearch;
	EditText etAddCitySearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcity);

		// ע��㲥
		AddCityBoradcast serbro = new AddCityBoradcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.weather.addcity");
		registerReceiver(serbro, filter);

		tvCancle = (TextView) findViewById(R.id.tv_addcity_cancle);
		cityList = new ArrayList<String>();
		String[] strCity = { "����", "�Ϻ�", "����", "����", "���", "����", "��ݸ", "����",
				"����", "�ɶ�", "����", "�Ͼ�", "����", "�人", "����", "����", "����", "����",
				"����", "����", "����", "�ൺ", "֣��", "����", "̫ԭ", "�Ϸ�", "������", "��ɳ",
				"ʯ��ׯ", "�ϲ�", "�麣", "���", "����", "̨��" };
		for (int i = 0; i < strCity.length; i++) {
			cityList.add(strCity[i]);
		}
		mGridView = (GridView) findViewById(R.id.gridview);
		ivAddCitySearch = (ImageView) findViewById(R.id.iv_addcity_search);
		etAddCitySearch = (EditText) findViewById(R.id.et_add_city_search);
		
		CityAdapter cityAdapter = new CityAdapter(cityList, this);
		mGridView.setAdapter(cityAdapter);

		// ����������
		tvCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AddCity.this, MyViewPager.class);
				AddCity.this.startActivity(intent);
			}
		});

		// ѡ�������
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tvCity = (TextView) arg1.findViewById(R.id.grid_item_city);
				Intent intent = new Intent("com.example.weather.viewpager");
				intent.putExtra("cityName", tvCity.getText());
				sendBroadcast(intent);
				Intent intent2 = new Intent(AddCity.this, MyViewPager.class);
				startActivity(intent2);
			}
		});
		
		
		ivAddCitySearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String city = etAddCitySearch.getText().toString();
				if(city!=null){
					Intent intent = new Intent("com.example.weather.viewpager");
					intent.putExtra("cityName", city);
					sendBroadcast(intent);
					Intent intent2 = new Intent(AddCity.this, MyViewPager.class);
					startActivity(intent2);
				}else{
					Toast.makeText(AddCity.this, "δ�������", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
	}

	// �½��㲥
	public class AddCityBoradcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub

		}

	}

}
