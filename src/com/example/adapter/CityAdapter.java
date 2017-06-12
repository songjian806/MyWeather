package com.example.adapter;

import java.util.ArrayList;

import com.example.myweather.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter {
	ArrayList<String> cityList;
	Context context;
	LayoutInflater inflater;
	public CityAdapter(ArrayList<String> cityList, Context context) {
		super();
		this.cityList = cityList;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cityList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = inflater.inflate(R.layout.grid_item, null);
		ImageView imge = (ImageView) view.findViewById(R.id.gridview);
		TextView text = (TextView) view.findViewById(R.id.grid_item_city);
		text.setText((CharSequence) cityList.get(arg0));
		return view;
	}

}
