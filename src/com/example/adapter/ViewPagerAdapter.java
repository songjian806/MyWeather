package com.example.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.WeatherInfo;
import com.example.myweather.R;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {
	
	List<View> mList;
	TextView mainWendu;	
	
	public ViewPagerAdapter(List<View> mList) {
		super();
		this.mList = mList;			
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup, int)
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View view = mList.get(position);
		container.addView(view);
		
		return view;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#setPrimaryItem(android.view.View, int, java.lang.Object)
	 */
	@Override
	public void setPrimaryItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		super.setPrimaryItem(container, position, object);

		
	}
	
	

}
