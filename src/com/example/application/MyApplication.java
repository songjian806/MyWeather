package com.example.application;

import com.thinkland.sdk.android.JuheSDKInitializer;

import android.app.Application;

public class MyApplication extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		JuheSDKInitializer.initialize(getApplicationContext());

	}
	
}
